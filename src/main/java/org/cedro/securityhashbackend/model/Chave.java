package org.cedro.securityhashbackend.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.cedro.securityhashbackend.model.jackson.KeyPairSerializer;
import org.hibernate.annotations.UuidGenerator;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.Base64;


@Entity
public class Chave {

    @Id
    @UuidGenerator
    private String id;

    @Column(name = "keyPar", length = 5000)
    @JsonSerialize(using = KeyPairSerializer.class)
    private KeyPair keyPar;


    public static KeyPair generateRSAKeyPair() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048); // Tamanho da chave RSA
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        return keyPair;
    }


    public Chave() {
    }

    public Chave(String id, KeyPair keyPar) {
        this.id = id;
        this.keyPar = keyPar;
    }

    public static String encryptData(String data, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedData = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encryptedData);
    }

    // Descriptografar com AES
    public static String decryptData(String encryptedData, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedData = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
        return new String(decryptedData);
    }

    public KeyPair getKeyPar() {
        return keyPar;
    }

    public void setKeyPar(KeyPair keyPar) {
        this.keyPar = keyPar;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


}
