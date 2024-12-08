package org.cedro.securityhashbackend.controller;

import jakarta.transaction.Transactional;
import org.cedro.securityhashbackend.model.Chave;
import org.cedro.securityhashbackend.repository.ChaveAESRepository;
import org.cedro.securityhashbackend.repository.ChaveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.KeyPair;
import java.util.List;

@RestController
@RequestMapping("/rsa/chaves")
@CrossOrigin(origins = "http://localhost:3000")
public class ChaveController {


    @Autowired
    private final ChaveRepository chaveRepository;

    private final ChaveAESRepository chaveAESRepository;

    public ChaveController(ChaveRepository chaveRepository, ChaveAESRepository chaveAESRepository) {
        this.chaveRepository = chaveRepository;
        this.chaveAESRepository = chaveAESRepository;
    }


    @GetMapping()
    public ResponseEntity<List<Chave>> findAll() {
        return ResponseEntity.ok(chaveRepository.findAll());
    }


    @PostMapping("/gerarChaves")
    @Transactional
    public ResponseEntity<Chave> gerarChaveRSA() {
        try {

            KeyPair keyPair = Chave.generateRSAKeyPair();

            Chave chave = new Chave();
            chave.setKeyPar(keyPair);
            // Salvar no banco de dados
            this.chaveRepository.save(chave);

            return ResponseEntity.ok().body(chave);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }


//    @GetMapping("/private")
//    public ResponseEntity<List<Chave>> privateKey() {
//        return ResponseEntity.ok(chaveRepository.findByTipo("privada"));
//    }

    @PostMapping()
    public ResponseEntity<Chave> salvarChave(@RequestBody Chave chave) {
        return ResponseEntity.ok(this.chaveRepository.save(chave));
    }


}
