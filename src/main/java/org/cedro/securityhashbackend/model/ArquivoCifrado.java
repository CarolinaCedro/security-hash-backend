package org.cedro.securityhashbackend.model;


import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import javax.crypto.SecretKey;

@Entity
public class ArquivoCifrado {

    @Id
    @UuidGenerator
    private String id;

    @Lob // Usado para armazenar grandes volumes de dados, como arquivos binários
    private String arquivoCifradoBase64;

    private String tipoArquivo;
    private String nomeArquivo;
    @Column(name = "chaveAESId", length = 5000)
    private SecretKey chaveAESId;

    @ManyToOne
    @JoinColumn(name = "chave_aes_id", referencedColumnName = "id", insertable = false, updatable = false)
    private ChaveAES chaveAES;

    // Construtores, getters e setters

    public ArquivoCifrado() {}

    public ArquivoCifrado(String id, String arquivoCifradoBase64, String tipoArquivo, String nomeArquivo, SecretKey chaveAESId, ChaveAES chaveAES) {
        this.id = id;
        this.arquivoCifradoBase64 = arquivoCifradoBase64;
        this.tipoArquivo = tipoArquivo;
        this.nomeArquivo = nomeArquivo;
        this.chaveAESId = chaveAESId;
        this.chaveAES = chaveAES;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getArquivoCifradoBase64() {
        return arquivoCifradoBase64;
    }

    public void setArquivoCifradoBase64(String arquivoCifradoBase64) {
        this.arquivoCifradoBase64 = arquivoCifradoBase64;
    }

    public String getTipoArquivo() {
        return tipoArquivo;
    }

    public void setTipoArquivo(String tipoArquivo) {
        this.tipoArquivo = tipoArquivo;
    }

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    public SecretKey getChaveAESId() {
        return chaveAESId;
    }

    public void setChaveAESId(SecretKey chaveAESId) {
        this.chaveAESId = chaveAESId;
    }

    public ChaveAES getChaveAES() {
        return chaveAES;
    }

    public void setChaveAES(ChaveAES chaveAES) {
        this.chaveAES = chaveAES;
    }
}
