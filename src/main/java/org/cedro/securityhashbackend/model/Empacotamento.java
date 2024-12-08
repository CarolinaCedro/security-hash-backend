package org.cedro.securityhashbackend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import org.hibernate.annotations.UuidGenerator;

import java.io.File;
import java.time.LocalDateTime;

@Entity
public class Empacotamento {

    @Id
    @UuidGenerator
    private String id;


    private String nomeArquivo;

    @Lob // Define que o campo é um grande objeto binário
    private File conteudo;

    private LocalDateTime dataCriacao;

    public Empacotamento(String id, String nomeArquivo, File conteudo, LocalDateTime dataCriacao) {
        this.id = id;
        this.nomeArquivo = nomeArquivo;
        this.conteudo = conteudo;
        this.dataCriacao = dataCriacao;
    }

    public Empacotamento() {
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

    public File getConteudo() {
        return conteudo;
    }

    public void setConteudo(File conteudo) {
        this.conteudo = conteudo;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
}
