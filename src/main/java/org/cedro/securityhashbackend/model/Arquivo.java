package org.cedro.securityhashbackend.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import org.hibernate.annotations.UuidGenerator;

@Entity
public class Arquivo {

    @Id
    @UuidGenerator
    private String id;

    private String nomeArquivo;
    private String tipoArquivo;
    @Lob
    @Column(name = "arquivo_original")
    private byte[] arquivoOriginal; // Arquivo em formato binário

    public Arquivo(String id, String nomeArquivo, String tipoArquivo, byte[] arquivoOriginal) {
        this.id = id;
        this.nomeArquivo = nomeArquivo;
        this.tipoArquivo = tipoArquivo;
        this.arquivoOriginal = arquivoOriginal;
    }

    public Arquivo() {
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    public String getTipoArquivo() {
        return tipoArquivo;
    }

    public void setTipoArquivo(String tipoArquivo) {
        this.tipoArquivo = tipoArquivo;
    }

    public byte[] getArquivoOriginal() {
        return arquivoOriginal;
    }

    public void setArquivoOriginal(byte[] arquivoOriginal) {
        this.arquivoOriginal = arquivoOriginal;
    }
}
