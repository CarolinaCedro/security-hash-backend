package org.cedro.securityhashbackend.service;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import java.security.PrivateKey;
import java.util.Base64;

public class ChaveAESService {

    // Método para descriptografar a chave AES com a chave privada RSA
    public SecretKey descriptografarChaveSimetrica(String chaveSimetricaCifradaBase64, PrivateKey chavePrivada) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA"); // Algoritmo RSA
        cipher.init(Cipher.DECRYPT_MODE, chavePrivada);

        // Decodificar a chave simétrica cifrada em Base64
        byte[] chaveSimetricaCifrada = Base64.getDecoder().decode(chaveSimetricaCifradaBase64);

        // Descriptografar a chave simétrica
        byte[] chaveSimetricaDecifrada = cipher.doFinal(chaveSimetricaCifrada);

        // Criar o SecretKey a partir dos dados decifrados
        return new javax.crypto.spec.SecretKeySpec(chaveSimetricaDecifrada, 0, chaveSimetricaDecifrada.length, "AES");
    }
}
