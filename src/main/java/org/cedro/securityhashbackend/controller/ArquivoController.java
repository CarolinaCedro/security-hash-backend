package org.cedro.securityhashbackend.controller;

import org.cedro.securityhashbackend.model.Arquivo;
import org.cedro.securityhashbackend.repository.ArquivoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/arquivo")
@CrossOrigin(origins = "http://localhost:3000")
public class ArquivoController {


    @Autowired
    private final ArquivoRepository arquivoRepository;

    public ArquivoController(ArquivoRepository arquivoRepository) {
        this.arquivoRepository = arquivoRepository;
    }


    @GetMapping()
    public ResponseEntity<List<Arquivo>> findAll() {
        return ResponseEntity.ok(arquivoRepository.findAll());
    }

    @PostMapping()
    public ResponseEntity<Arquivo> salvarChave(@RequestBody Arquivo chave) {
        return ResponseEntity.ok(this.arquivoRepository.save(chave));
    }


}
