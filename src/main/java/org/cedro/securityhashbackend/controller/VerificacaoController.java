package org.cedro.securityhashbackend.controller;

import org.cedro.securityhashbackend.repository.AssinaturaRepository;
import org.cedro.securityhashbackend.repository.ChaveRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/verificacao")
@CrossOrigin(origins = "http://localhost:3000")
public class VerificacaoController {

    private final ChaveRepository chaveRepository;
    private final AssinaturaRepository assinaturaRepository;

    public VerificacaoController(ChaveRepository chaveRepository, AssinaturaRepository assinaturaRepository) {
        this.chaveRepository = chaveRepository;
        this.assinaturaRepository = assinaturaRepository;
    }


}

