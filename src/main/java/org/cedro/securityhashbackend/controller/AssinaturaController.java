package org.cedro.securityhashbackend.controller;

import org.cedro.securityhashbackend.model.*;
import org.cedro.securityhashbackend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;
import java.security.PrivateKey;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/assinatura")
@CrossOrigin(origins = "http://localhost:3000")
public class AssinaturaController {


    @Autowired
    private final AssinaturaRepository assinaturaRepository;

    private final ChaveAESRepository chaveAESRepository;
    private final ArquivoRepository arquivoRepository;
    private final ArquivoCifradoRepository arquivoCifradoRepository;
    private final ChaveRepository chaveRepository;

    public AssinaturaController(AssinaturaRepository assinaturaRepository, ChaveAESRepository chaveAESRepository, ArquivoRepository arquivoRepository, ArquivoCifradoRepository arquivoCifradoRepository, ChaveRepository chaveRepository) {
        this.assinaturaRepository = assinaturaRepository;
        this.chaveAESRepository = chaveAESRepository;
        this.arquivoRepository = arquivoRepository;
        this.arquivoCifradoRepository = arquivoCifradoRepository;
        this.chaveRepository = chaveRepository;
    }


    @GetMapping()
    public ResponseEntity<List<Assinatura>> findAll() {
        return ResponseEntity.ok(assinaturaRepository.findAll());
    }

    @PostMapping()
    public ResponseEntity<Assinatura> salvarChave(@RequestBody Assinatura chave) {
        return ResponseEntity.ok(this.assinaturaRepository.save(chave));
    }

    @PostMapping("/assinar")
    public ResponseEntity<?> assinarDados() {
        try {
            System.out.println("Iniciando o processo de assinatura.");

            // Recuperar todas as chaves privadas e arquivos
            List<Chave> chavesPrivadas = chaveRepository.findAll();
            List<Arquivo> arquivos = arquivoRepository.findAll();

            System.out.println("Chaves privadas recuperadas: " + chavesPrivadas.size());
            System.out.println("Arquivos recuperados: " + arquivos.size());

            // Validar existência de chave privada e arquivo
            if (chavesPrivadas.isEmpty()) {
                System.out.println("Nenhuma chave privada encontrada.");
                return ResponseEntity.status(404).body("Nenhuma chave privada encontrada.");
            }

            if (arquivos.isEmpty()) {
                System.out.println("Nenhum arquivo encontrado.");
                return ResponseEntity.status(404).body("Nenhum arquivo encontrado.");
            }

            // Pegar a última chave privada e o último arquivo
            Chave chavePrivada = chavesPrivadas.get(chavesPrivadas.size() - 1);
            Arquivo arquivoRecuperado = arquivos.get(arquivos.size() - 1);

            System.out.println("Chave privada selecionada: " + chavePrivada.getId());
            System.out.println("Arquivo selecionado: " + arquivoRecuperado.getNomeArquivo());

            // Assinar os dados
            if (chavePrivada.getKeyPar() == null) {
                System.out.println("Erro: O campo keyPar está nulo para a chave selecionada.");
                return ResponseEntity.status(500).body("Erro: O campo keyPar está nulo para a chave selecionada.");
            }

            PrivateKey privateKey = chavePrivada.getKeyPar().getPrivate();

            // Assine os dados diretamente (sem Base64)
            byte[] assinatura = Assinatura.signData(arquivoRecuperado.getArquivoOriginal(), privateKey);
            System.out.println("Assinatura gerada com sucesso.");

            // Salvar a assinatura no banco
            Assinatura assinaturaObjeto = new Assinatura();
            assinaturaObjeto.setArquivoAssinado(Base64.getEncoder().encodeToString(assinatura));
            assinaturaRepository.save(assinaturaObjeto);

            System.out.println("Assinatura salva com sucesso no banco.");

            return ResponseEntity.ok(assinaturaObjeto);

        } catch (Exception e) {
            // Retornar erro detalhado para o cliente
            System.out.println("Erro ao assinar os dados: " + e.getMessage());
            return ResponseEntity.status(500).body("Erro ao assinar os dados: " + e.getMessage());
        }
    }

    // Método para cifrar os dados com a chave AES
    @PostMapping("/cifrar")
    public ResponseEntity<?> cifrarArquivo() {
        try {
            System.out.println("Iniciando o processo de cifragem.");

            // Validar entrada
            List<Arquivo> arquivos = arquivoRepository.findAll();
            List<ChaveAES> aes = chaveAESRepository.findAll();

            System.out.println("Arquivos recuperados: " + arquivos.size());
            System.out.println("Chaves AES recuperadas: " + aes.size());

            // Pegar a última chave privada e o último arquivo
            Arquivo arquivo = arquivos.get(arquivos.size() - 1);
            ChaveAES chaveSimetrica = aes.get(aes.size() - 1);

            System.out.println("Arquivo selecionado: " + arquivo.getNomeArquivo());
            System.out.println("Chave AES selecionada: " + chaveSimetrica.getId());

            // Converter arquivo para String (usando Base64 para evitar problemas com dados binários)
            String arquivoString = Base64.getEncoder().encodeToString(arquivo.getArquivoOriginal());

            // Converter a chave Base64 para SecretKey
            SecretKey secretKey = new javax.crypto.spec.SecretKeySpec(
                    Base64.getDecoder().decode(chaveSimetrica.getChaveBase64()),
                    "AES"
            );

            // Cifrar o conteúdo do arquivo
            String dadosCifrados = ChaveAES.encryptData(arquivoString, secretKey);
            System.out.println("Arquivo cifrado com sucesso.");

            ArquivoCifrado arquivoCifrado = new ArquivoCifrado();
            arquivoCifrado.setArquivoCifradoBase64(dadosCifrados);
            arquivoCifrado.setTipoArquivo(arquivo.getTipoArquivo());
            arquivoCifrado.setNomeArquivo(arquivo.getNomeArquivo());
            arquivoCifrado.setChaveAESId(secretKey);

            this.arquivoCifradoRepository.save(arquivoCifrado);
            System.out.println("Arquivo cifrado salvo com sucesso no banco.");

            return ResponseEntity.ok(dadosCifrados);
        } catch (IllegalArgumentException e) {
            // Caso os dados da chave sejam inválidos
            System.out.println("Chave AES inválida: " + e.getMessage());
            return ResponseEntity.status(400).body("Chave AES inválida: " + e.getMessage());
        } catch (Exception e) {
            // Qualquer outro erro
            System.out.println("Erro ao cifrar o arquivo: " + e.getMessage());
            return ResponseEntity.status(500).body("Erro ao cifrar o arquivo: " + e.getMessage());
        }
    }


}
