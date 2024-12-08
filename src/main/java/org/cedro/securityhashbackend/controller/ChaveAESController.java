package org.cedro.securityhashbackend.controller;

import org.cedro.securityhashbackend.model.Chave;
import org.cedro.securityhashbackend.model.ChaveAES;
import org.cedro.securityhashbackend.model.ChaveSimetricaCifrada;
import org.cedro.securityhashbackend.repository.ChaveAESRepository;
import org.cedro.securityhashbackend.repository.ChaveRepository;
import org.cedro.securityhashbackend.repository.ChaveSimetricaCifradaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.PublicKey;
import java.util.Base64;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/aes/chaves")
@CrossOrigin(origins = "http://localhost:3000")
public class ChaveAESController {

    private final ChaveAESRepository chaveAESRepository;
    private final ChaveRepository chaveRepository;  // Repositório para buscar a chave pública
    private final ChaveSimetricaCifradaRepository chaveSimetricaCifradaRepository;

    public ChaveAESController(ChaveAESRepository chaveAESRepository, ChaveRepository chaveRepository, ChaveSimetricaCifradaRepository chaveSimetricaCifradaRepository) {
        this.chaveAESRepository = chaveAESRepository;
        this.chaveRepository = chaveRepository;
        this.chaveSimetricaCifradaRepository = chaveSimetricaCifradaRepository;
    }

    @GetMapping()
    public ResponseEntity<List<ChaveAES>> findAll() {
        return ResponseEntity.ok(chaveAESRepository.findAll());
    }

    @PostMapping("/salvar")
    public ResponseEntity<ChaveAES> salvarChave(@RequestBody ChaveAES chavePem) {
        return ResponseEntity.ok(chaveAESRepository.save(chavePem));
    }


    @PostMapping("/gerar")
    public ResponseEntity<String> gerarChaveAES() {
        try {
            // Gerar uma chave AES com tamanho de 256 bits
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(256); // Defina o tamanho da chave
            SecretKey secretKey = keyGenerator.generateKey();

            // Codificar a chave AES para Base64 para facilitar o transporte
            String chaveAESBase64 = Base64.getEncoder().encodeToString(secretKey.getEncoded());

            // Opcional: salvar no repositório (remova se não precisar persistir)
            ChaveAES chaveAES = new ChaveAES();
            chaveAES.setChaveBase64(chaveAESBase64); // Supondo que exista um campo 'chave' no seu modelo
            chaveAESRepository.save(chaveAES);

            // Retornar a chave gerada em Base64
            return ResponseEntity.ok(chaveAESBase64);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao gerar a chave AES: " + e.getMessage());
        }
    }


    //     Método para cifrar a chave simétrica AES usando a chave pública do destinatário
    @PostMapping("/cifrar")
    public ResponseEntity<?> cifrarChaveAES() {
        try {


            // Recuperar a chave pública do destinatário (primeira chave pública encontrada)
            List<Chave> chavePublica = chaveRepository.findAll();
            List<ChaveAES> chaveSimetrica = chaveAESRepository.findAll();

            Chave chavePublicaRecebida = chavePublica.get(chavePublica.size() - 1);
            ChaveAES chaveSimetricaRecebida = chaveSimetrica.get(chaveSimetrica.size() - 1);

            PublicKey publicKey = chavePublicaRecebida.getKeyPar().getPublic();

            // Decodificar a chave AES de Base64
            byte[] chaveAESBytes = Base64.getDecoder().decode(chaveSimetricaRecebida.getChaveBase64());

            // Cifrar a chave AES com a chave pública RSA do destinatário
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] chaveAESCifrada = cipher.doFinal(chaveAESBytes);

            // Salvar a chave AES cifrada no banco de dados
            ChaveSimetricaCifrada chaveSimetricaCifrada = new ChaveSimetricaCifrada();
            chaveSimetricaCifrada.setChaveSimetricaCifraca(Base64.getEncoder().encodeToString(chaveAESCifrada));

            chaveSimetricaCifradaRepository.save(chaveSimetricaCifrada);
            return ResponseEntity.ok(chaveSimetricaCifrada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body("Formato inválido para a chave AES fornecida.");
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(404).body("Chave pública não encontrada.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao cifrar a chave AES: " + e.getMessage());
        }
    }


}
