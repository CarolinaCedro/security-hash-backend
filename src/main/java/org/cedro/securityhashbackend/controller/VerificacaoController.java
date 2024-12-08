package org.cedro.securityhashbackend.controller;

import org.cedro.securityhashbackend.model.Assinatura;
import org.cedro.securityhashbackend.model.Chave;
import org.cedro.securityhashbackend.model.DadosEntrada;
import org.cedro.securityhashbackend.repository.AssinaturaRepository;
import org.cedro.securityhashbackend.repository.ChaveRepository;
import org.cedro.securityhashbackend.service.ArquivoService;
import org.cedro.securityhashbackend.service.AssinaturaService;
import org.cedro.securityhashbackend.service.ChaveAESService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.List;

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

//    @PostMapping("/verificar")
//    public ResponseEntity<String> verificarETrazerArquivoOriginal(@RequestBody DadosEntrada dadosEntrada) {
//        try {
//            // Recuperar chave pública do destinatário
//            List<Chave> chavesPublicas = chaveRepository.findByTipo("publica");
//            if (chavesPublicas.isEmpty()) {
//                return ResponseEntity.status(400).body("Chave pública não encontrada.");
//            }
//            PublicKey chavePublicaRSA = chavesPublicas.get(0).getKeyPar().getPublic();
//
//            // Verificar assinatura
//            Assinatura assinatura = assinaturaRepository.findById(dadosEntrada.getAssinaturaId())
//                    .orElseThrow(() -> new Exception("Assinatura não encontrada"));
//            AssinaturaService assinaturaService = new AssinaturaService();
//
//            // Usar o método correto getArquivoAssinado() para acessar a assinatura
//            boolean assinaturaValida = assinaturaService.verificarAssinatura(dadosEntrada.getDados(), assinatura.getArquivoAssinado(), chavePublicaRSA);
//
//            if (!assinaturaValida) {
//                return ResponseEntity.status(400).body("Assinatura inválida.");
//            }
//
//            // Descriptografar a chave simétrica com a chave privada
//            List<Chave> chavesPrivadas = chaveRepository.findByTipo("privada");
//            if (chavesPrivadas.isEmpty()) {
//                return ResponseEntity.status(400).body("Chave privada não encontrada.");
//            }
//            PrivateKey chavePrivadaRSA = chavesPrivadas.get(0).getKeyPar().getPrivate();
//
//            ChaveAESService chaveAESService = new ChaveAESService();
//            SecretKey chaveSimetrica = chaveAESService.descriptografarChaveSimetrica(dadosEntrada.getChaveSimetricaCifrada(), chavePrivadaRSA);
//
//            // Descriptografar o arquivo
//            ArquivoService arquivoService = new ArquivoService();
//            String arquivoOriginal = arquivoService.descriptografarArquivo(dadosEntrada.getArquivoCifrado(), chaveSimetrica);
//
//            return ResponseEntity.ok(arquivoOriginal);
//        } catch (Exception e) {
//            return ResponseEntity.status(500).body("Erro: " + e.getMessage());
//        }
//    }

}

