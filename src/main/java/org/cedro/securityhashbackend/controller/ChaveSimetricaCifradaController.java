package org.cedro.securityhashbackend.controller;

import org.cedro.securityhashbackend.model.ChaveSimetricaCifrada;
import org.cedro.securityhashbackend.repository.ChaveSimetricaCifradaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chave-simetrica-cifrada")
@CrossOrigin(origins = "http://localhost:3000")
public class ChaveSimetricaCifradaController {


    @Autowired
    private final ChaveSimetricaCifradaRepository chaveSimetricaCifradaRepository;

    public ChaveSimetricaCifradaController(ChaveSimetricaCifradaRepository chaveSimetricaCifradaRepository) {
        this.chaveSimetricaCifradaRepository = chaveSimetricaCifradaRepository;
    }


    @GetMapping()
    public ResponseEntity<List<ChaveSimetricaCifrada>> findAll() {
        return ResponseEntity.ok(chaveSimetricaCifradaRepository.findAll());
    }


    @PostMapping()
    public ResponseEntity<ChaveSimetricaCifrada> salvarChave(@RequestBody ChaveSimetricaCifrada chave) {
        return ResponseEntity.ok(this.chaveSimetricaCifradaRepository.save(chave));
    }


}
