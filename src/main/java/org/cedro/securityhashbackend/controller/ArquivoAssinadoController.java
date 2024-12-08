package org.cedro.securityhashbackend.controller;

import org.cedro.securityhashbackend.model.ArquivoAssinado;
import org.cedro.securityhashbackend.repository.ArquivoAssinadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/arquivo-assinado")
@CrossOrigin(origins = "http://localhost:3000")
public class ArquivoAssinadoController {


    @Autowired
    private final ArquivoAssinadoRepository arquivoAssinadoRepository;

    public ArquivoAssinadoController(ArquivoAssinadoRepository arquivoAssinadoRepository) {
        this.arquivoAssinadoRepository = arquivoAssinadoRepository;
    }


    @GetMapping()
    public ResponseEntity<List<ArquivoAssinado>> findAll() {
        return ResponseEntity.ok(arquivoAssinadoRepository.findAll());
    }

    @PostMapping()
    public ResponseEntity<ArquivoAssinado> salvarChave(@RequestBody ArquivoAssinado chave) {
        return ResponseEntity.ok(this.arquivoAssinadoRepository.save(chave));
    }


}
