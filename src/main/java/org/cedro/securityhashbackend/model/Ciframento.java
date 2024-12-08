package org.cedro.securityhashbackend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.hibernate.annotations.UuidGenerator;


@Entity
public class Ciframento {

    @Id
    @UuidGenerator
    private String id;

    private String chaveAESBase64;     //Base64
    private String arquivoCifrado;     //Base64
    private String assinatura;         //Base64
    private String tipoArquivo;         //Base64


    public Ciframento(String id, String chaveAESBase64, String arquivoCifrado, String assinatura, String tipoArquivo) {
        this.id = id;
        this.chaveAESBase64 = chaveAESBase64;
        this.arquivoCifrado = arquivoCifrado;
        this.assinatura = assinatura;
        this.tipoArquivo = tipoArquivo;
    }

    public Ciframento() {
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChaveAESBase64() {
        return chaveAESBase64;
    }

    public void setChaveAESBase64(String chaveAESBase64) {
        this.chaveAESBase64 = chaveAESBase64;
    }

    public String getArquivoCifrado() {
        return arquivoCifrado;
    }

    public void setArquivoCifrado(String arquivoCifrado) {
        this.arquivoCifrado = arquivoCifrado;
    }

    public String getAssinatura() {
        return assinatura;
    }

    public void setAssinatura(String assinatura) {
        this.assinatura = assinatura;
    }

    public String getTipoArquivo() {
        return tipoArquivo;
    }

    public void setTipoArquivo(String tipoArquivo) {
        this.tipoArquivo = tipoArquivo;
    }
}

