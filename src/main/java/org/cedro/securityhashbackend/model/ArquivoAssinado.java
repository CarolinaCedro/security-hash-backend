package org.cedro.securityhashbackend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.hibernate.annotations.UuidGenerator;

@Entity
public class ArquivoAssinado {

    @Id
    @UuidGenerator
    private String id;

    private byte[] arquivoAssinado;
    private byte[] nonce;

    public ArquivoAssinado(String id, byte[] arquivoAssinado, byte[] nonce) {
        this.id = id;
        this.arquivoAssinado = arquivoAssinado;
        this.nonce = nonce;
    }

    public ArquivoAssinado() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public byte[] getArquivoAssinado() {
        return arquivoAssinado;
    }

    public void setArquivoAssinado(byte[] arquivoAssinado) {
        this.arquivoAssinado = arquivoAssinado;
    }

    public byte[] getNonce() {
        return nonce;
    }

    public void setNonce(byte[] nonce) {
        this.nonce = nonce;
    }
}
