package org.cedro.securityhashbackend.controller;

import org.cedro.securityhashbackend.model.ArquivoCifrado;
import org.cedro.securityhashbackend.repository.ArquivoCifradoRepository;
import org.cedro.securityhashbackend.repository.ChaveRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/arquivo-cifrado")
@CrossOrigin(origins = "http://localhost:3000")
public class ArquivoCifradoController {

    private final ArquivoCifradoRepository arquivoCifradoRepository;
    private final ChaveRepository chaveRepository;  // Repositório para buscar a chave pública

    public ArquivoCifradoController(ArquivoCifradoRepository arquivoCifradoRepository, ChaveRepository chaveRepository) {
        this.arquivoCifradoRepository = arquivoCifradoRepository;
        this.chaveRepository = chaveRepository;
    }


    @GetMapping()
    public ResponseEntity<List<ArquivoCifrado>> findAll() {
        return ResponseEntity.ok(arquivoCifradoRepository.findAll());
    }

    @PostMapping("/salvar")
    public ResponseEntity<ArquivoCifrado> salvarChave(@RequestBody ArquivoCifrado chavePem) {
        return ResponseEntity.ok(arquivoCifradoRepository.save(chavePem));
    }


}
