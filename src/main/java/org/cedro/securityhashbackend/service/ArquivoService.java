package org.cedro.securityhashbackend.service;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import java.util.Base64;

public class ArquivoService {

    // Método para descriptografar o arquivo
    public String descriptografarArquivo(String arquivoCifradoBase64, SecretKey chaveAES) throws Exception {
        Cipher cipher = Cipher.getInstance("AES"); // Algoritmo AES
        cipher.init(Cipher.DECRYPT_MODE, chaveAES);

        // Decodificar o arquivo cifrado
        byte[] arquivoCifrado = Base64.getDecoder().decode(arquivoCifradoBase64);

        // Descriptografar o arquivo
        byte[] arquivoDescriptografado = cipher.doFinal(arquivoCifrado);

        // Retornar o arquivo descriptografado como string (ou você pode retornar como bytes)
        return new String(arquivoDescriptografado);
    }
}

