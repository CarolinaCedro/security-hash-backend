package org.cedro.securityhashbackend.model;


public class DadosEntrada {
    private String dados;
    private String assinaturaId;
    private String chaveSimetricaCifrada;
    private String arquivoCifrado;

    public DadosEntrada(String dados, String assinaturaId, String chaveSimetricaCifrada, String arquivoCifrado) {
        this.dados = dados;
        this.assinaturaId = assinaturaId;
        this.chaveSimetricaCifrada = chaveSimetricaCifrada;
        this.arquivoCifrado = arquivoCifrado;
    }


    public String getDados() {
        return dados;
    }

    public void setDados(String dados) {
        this.dados = dados;
    }

    public String getAssinaturaId() {
        return assinaturaId;
    }

    public void setAssinaturaId(String assinaturaId) {
        this.assinaturaId = assinaturaId;
    }

    public String getChaveSimetricaCifrada() {
        return chaveSimetricaCifrada;
    }

    public void setChaveSimetricaCifrada(String chaveSimetricaCifrada) {
        this.chaveSimetricaCifrada = chaveSimetricaCifrada;
    }

    public String getArquivoCifrado() {
        return arquivoCifrado;
    }

    public void setArquivoCifrado(String arquivoCifrado) {
        this.arquivoCifrado = arquivoCifrado;
    }
}
