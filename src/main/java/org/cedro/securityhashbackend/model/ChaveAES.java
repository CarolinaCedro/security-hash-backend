package org.cedro.securityhashbackend.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.hibernate.annotations.UuidGenerator;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Base64;

@Entity
public class ChaveAES {

    @Id
    @UuidGenerator
    private String id;


    private String chaveBase64;  // Chave AES em Base64

    public ChaveAES(String chaveBase64) {
        this.chaveBase64 = chaveBase64;
    }

    public ChaveAES(String id, String chaveBase64) {
        this.id = id;
        this.chaveBase64 = chaveBase64;
    }

    public ChaveAES() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChaveBase64() {
        return chaveBase64;
    }

    public void setChaveBase64(String chaveBase64) {
        this.chaveBase64 = chaveBase64;
    }

    public static SecretKey generateAESKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(256); // Chave AES de 256 bits
        return keyGenerator.generateKey();
    }

    public static String encryptData(String dados, SecretKey secretKey) throws Exception {
        // Inicializa o Cipher para criptografar dados com AES
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        // Cifra os dados
        byte[] dadosCifrados = cipher.doFinal(dados.getBytes());

        // Converte os dados cifrados para Base64 para facilitar o armazenamento e transporte
        return Base64.getEncoder().encodeToString(dadosCifrados);
    }

}
