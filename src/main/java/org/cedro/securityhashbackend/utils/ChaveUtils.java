package org.cedro.securityhashbackend.utils;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class ChaveUtils {

    // Converter chave pública PEM para PublicKey
    public static PublicKey converterChavePublica(String chavePem) throws Exception {
        String chaveSemHeaderFooter = chavePem.replace("-----BEGIN PUBLIC KEY-----", "").replace("-----END PUBLIC KEY-----", "");
        byte[] chaveBytes = Base64.getDecoder().decode(chaveSemHeaderFooter);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(chaveBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(keySpec);
    }

    // Converter chave privada PEM para PrivateKey
    public static PrivateKey converterChavePrivada(String chavePem) throws Exception {
        String chaveSemHeaderFooter = chavePem.replace("-----BEGIN PRIVATE KEY-----", "").replace("-----END PRIVATE KEY-----", "");
        byte[] chaveBytes = Base64.getDecoder().decode(chaveSemHeaderFooter);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(chaveBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }

}
