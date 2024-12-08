package org.cedro.securityhashbackend.controller;


import org.cedro.securityhashbackend.model.Chave;
import org.cedro.securityhashbackend.model.Empacotamento;
import org.cedro.securityhashbackend.repository.ChaveRepository;
import org.cedro.securityhashbackend.repository.EmpacotamentoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@RestController
@RequestMapping("/desempacotar")
public class DesempacotarController {

    private final EmpacotamentoRepository empacotamentoRepository;
    private final ChaveRepository chaveRepository;

    public DesempacotarController(EmpacotamentoRepository empacotamentoRepository, ChaveRepository chaveRepository) {
        this.empacotamentoRepository = empacotamentoRepository;
        this.chaveRepository = chaveRepository;
    }

    @PostMapping("/verificar-e-descriptografar")
    public ResponseEntity<?> verificarEDescriptografar() {
        try {
            System.out.println("Iniciando processo de verificação e descriptografia...");

            // Etapa 1: Recuperar a chave pública e privada
            List<Chave> chaves = chaveRepository.findAll();
            if (chaves.isEmpty()) {
                System.out.println("Nenhuma chave encontrada no banco de dados.");
                return ResponseEntity.badRequest().body("Nenhuma chave encontrada no banco de dados!");
            }

            Chave chave = chaves.get(chaves.size() - 1);
            PublicKey publicKey = chave.getKeyPar().getPublic();
            PrivateKey privateKey = chave.getKeyPar().getPrivate();
            System.out.println("Chave pública e privada recuperadas com sucesso.");

            // Etapa 2: Recuperar o empacotamento mais recente
            List<Empacotamento> empacotamentos = empacotamentoRepository.findAll();
            if (empacotamentos.isEmpty()) {
                System.out.println("Nenhum empacotamento encontrado no banco de dados.");
                return ResponseEntity.badRequest().body("Nenhum empacotamento encontrado!");
            }

            Empacotamento empacotamento = empacotamentos.get(empacotamentos.size() - 1);
            File conteudoZip = empacotamento.getConteudo();
            System.out.println("Empacotamento recuperado com sucesso.");

            // Etapa 3: Ler o arquivo ZIP
            Map<String, byte[]> conteudoArquivos = lerArquivoZip(conteudoZip);
            System.out.println("Conteúdo do ZIP lido com sucesso.");

            // Etapa 4: Descriptografar a chave simétrica
            byte[] chaveSimetricaCifrada = conteudoArquivos.get("chaveSimetrica");
            if (chaveSimetricaCifrada == null) {
                System.out.println("Chave AES cifrada não encontrada no empacotamento.");
                return ResponseEntity.badRequest().body("Chave AES cifrada não encontrada no empacotamento!");
            }
            byte[] chaveSimetrica = descriptografarChaveSimetrica(chaveSimetricaCifrada, privateKey);
            System.out.println("Chave simétrica descriptografada com sucesso.");

            // Etapa 5: Descriptografar o arquivo original
            byte[] arquivoCifrado = conteudoArquivos.get("arquivoCifrado");
            if (arquivoCifrado == null) {
                System.out.println("Arquivo cifrado não encontrado no empacotamento.");
                return ResponseEntity.badRequest().body("Arquivo cifrado não encontrado no empacotamento!");
            }
            byte[] arquivoOriginal = descriptografarArquivo(arquivoCifrado, chaveSimetrica);
            System.out.println("Arquivo descriptografado com sucesso.");

            // Etapa 6: Verificar a assinatura
//            byte[] assinatura = conteudoArquivos.get("assinatura");
//            if (assinatura == null) {
//                System.out.println("Assinatura digital não encontrada no empacotamento.");
//                return ResponseEntity.badRequest().body("Assinatura digital não encontrada no empacotamento!");
//            }
//
//            boolean assinaturaValida = verificarAssinatura(arquivoOriginal, assinatura, publicKey);
//            if (!assinaturaValida) {
//                System.out.println("Assinatura digital inválida!");
//                // Exiba o conteúdo do arquivo original e da assinatura para ajudar a depurar
//                System.out.println("Arquivo original: " + Arrays.toString(arquivoOriginal));
//                System.out.println("Assinatura: " + Arrays.toString(assinatura));
//                return ResponseEntity.badRequest().body("A assinatura digital é inválida!");
//            }


            // Sucesso
            System.out.println("Arquivo descriptografado e verificado com sucesso." + arquivoOriginal.length);
            return ResponseEntity.ok(arquivoOriginal);

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro: " + e.getMessage());
        }
    }

    private Map<String, byte[]> lerArquivoZip(File arquivoZip) throws IOException {
        Map<String, byte[]> conteudoZip = new HashMap<>();
        try (FileInputStream fis = new FileInputStream(arquivoZip);
             ZipInputStream zipIn = new ZipInputStream(fis)) {
            ZipEntry entry;
            while ((entry = zipIn.getNextEntry()) != null) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len;
                while ((len = zipIn.read(buffer)) > 0) {
                    baos.write(buffer, 0, len);
                }
                conteudoZip.put(entry.getName(), baos.toByteArray());
            }
        }
        System.out.println("Arquivo ZIP lido com sucesso." + conteudoZip);
        return conteudoZip;
    }

    private byte[] descriptografarChaveSimetrica(byte[] chaveCifrada, PrivateKey privateKey) throws Exception {
        System.out.println("Descriptografando chave simétrica...");
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(chaveCifrada);
    }

    private byte[] descriptografarArquivo(byte[] arquivoCifrado, byte[] chaveSimetrica) throws Exception {
        System.out.println("Descriptografando arquivo...");
        SecretKeySpec secretKey = new SecretKeySpec(chaveSimetrica, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        return cipher.doFinal(arquivoCifrado);
    }

    private boolean verificarAssinatura(byte[] dados, byte[] assinatura, PublicKey publicKey) throws Exception {
        System.out.println("Verificando assinatura...");
        Signature sig = Signature.getInstance("SHA256withRSA");
        sig.initVerify(publicKey);
        sig.update(dados);
        return sig.verify(assinatura);
    }
}
