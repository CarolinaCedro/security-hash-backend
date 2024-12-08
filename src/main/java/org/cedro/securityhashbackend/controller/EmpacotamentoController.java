package org.cedro.securityhashbackend.controller;

import org.cedro.securityhashbackend.model.ArquivoCifrado;
import org.cedro.securityhashbackend.model.Assinatura;
import org.cedro.securityhashbackend.model.ChaveSimetricaCifrada;
import org.cedro.securityhashbackend.model.Empacotamento;
import org.cedro.securityhashbackend.repository.ArquivoCifradoRepository;
import org.cedro.securityhashbackend.repository.AssinaturaRepository;
import org.cedro.securityhashbackend.repository.ChaveSimetricaCifradaRepository;
import org.cedro.securityhashbackend.repository.EmpacotamentoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RestController
@RequestMapping("/empacotamento")
@CrossOrigin(origins = "http://localhost:3000")
public class EmpacotamentoController {


    private final ArquivoCifradoRepository arquivoCifradoRepository;
    private final AssinaturaRepository assinaturaRepository;
    private final EmpacotamentoRepository empacotamentoRepository;

    private final ChaveSimetricaCifradaRepository chaveSimetricaCifradaRepository;

    public EmpacotamentoController(ArquivoCifradoRepository arquivoCifradoRepository, AssinaturaRepository assinaturaRepository, EmpacotamentoRepository empacotamentoRepository, ChaveSimetricaCifradaRepository chaveSimetricaCifradaRepository) {
        this.arquivoCifradoRepository = arquivoCifradoRepository;
        this.assinaturaRepository = assinaturaRepository;
        this.empacotamentoRepository = empacotamentoRepository;
        this.chaveSimetricaCifradaRepository = chaveSimetricaCifradaRepository;
    }


    @PostMapping("/empacotar")
    public ResponseEntity<String> empacotar() {
        try {
            //arquivo cifrado, assinatura e chave

            List<ArquivoCifrado> arquivoCifrado = arquivoCifradoRepository.findAll();
            List<Assinatura> assinatura = assinaturaRepository.findAll();
            List<ChaveSimetricaCifrada> chaveSimetricaCifradas = chaveSimetricaCifradaRepository.findAll();

            ArquivoCifrado arquivoCifradoEncontrado = arquivoCifrado.get(arquivoCifrado.size() - 1);
            Assinatura assinaturaEncontrado = assinatura.get(assinatura.size() - 1);
            ChaveSimetricaCifrada chaveSimetricaCifrada = chaveSimetricaCifradas.get(chaveSimetricaCifradas.size() - 1);

            System.out.println("A assinatura original => "+ assinaturaEncontrado.getArquivoAssinado());


            //Transformando os arquivos em zip antes pq é mais facil pra empacotar

            byte[] arquivoCifradoByte = Base64.getDecoder().decode(arquivoCifradoEncontrado.getArquivoCifradoBase64());
            byte[] assinaturaByte = Base64.getDecoder().decode(assinaturaEncontrado.getArquivoAssinado());

            System.out.println("A assinatura depois em zip => "+ assinaturaByte);

            byte[] chaveSimetricaByte = Base64.getDecoder().decode(chaveSimetricaCifrada.getChaveSimetricaCifraca());


            // Empacotar tudo em um único arquivo
            File arquivoEmpacotado = criarArquivoEmpacotado(arquivoCifradoByte, assinaturaByte, chaveSimetricaByte);


            Empacotamento empacotamento = new Empacotamento();
            empacotamento.setNomeArquivo(arquivoCifradoEncontrado.getNomeArquivo());
            empacotamento.setDataCriacao(LocalDateTime.now());
            empacotamento.setConteudo(arquivoEmpacotado);

            empacotamentoRepository.save(empacotamento);

            return ResponseEntity.ok("Arquivo empacotado com sucesso: " + arquivoEmpacotado.getAbsolutePath());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro no empacotamento: " + e.getMessage());
        }
    }

    private File criarArquivoEmpacotado(byte[] arquivoCifrado, byte[] assinatura, byte[] chaveSimetrica) throws IOException {
        // Criando o arquivo ZIP
        File arquivoEmpacotado = new File("arquivoEmpacotado.zip");
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(arquivoEmpacotado))) {
            // Adicionando arquivo cifrado ao ZIP
            zipOutputStream.putNextEntry(new ZipEntry("arquivoCifrado"));
            zipOutputStream.write(arquivoCifrado);
            zipOutputStream.closeEntry();

            // Adicionando assinatura ao ZIP
            zipOutputStream.putNextEntry(new ZipEntry("assinatura"));
            zipOutputStream.write(assinatura);
            zipOutputStream.closeEntry();

            // Adicionando chave simétrica cifrada ao ZIP
            zipOutputStream.putNextEntry(new ZipEntry("chaveSimetrica"));
            zipOutputStream.write(chaveSimetrica);
            zipOutputStream.closeEntry();
        }
        return arquivoEmpacotado;
    }


}
