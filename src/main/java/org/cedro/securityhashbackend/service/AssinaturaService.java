package org.cedro.securityhashbackend.service;

import java.security.PublicKey;
import java.security.Signature;
import java.util.Base64;

public class AssinaturaService {

    // Método para verificar a assinatura
    public boolean verificarAssinatura(String dados, String assinaturaBase64, PublicKey chavePublica) throws Exception {
        Signature signature = Signature.getInstance("SHA256withRSA");  // Algoritmo de assinatura

        // Converter assinatura de Base64 para byte[]
        byte[] assinatura = Base64.getDecoder().decode(assinaturaBase64);

        // Inicializar a assinatura com a chave pública
        signature.initVerify(chavePublica);
        signature.update(dados.getBytes());

        // Verificar a assinatura
        return signature.verify(assinatura);
    }
}
