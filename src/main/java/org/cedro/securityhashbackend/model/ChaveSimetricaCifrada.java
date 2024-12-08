package org.cedro.securityhashbackend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.hibernate.annotations.UuidGenerator;

@Entity
public class ChaveSimetricaCifrada {

    @Id
    @UuidGenerator
    private String id;


    @Column(name = "chave_pem", length = 5000)
    private String chaveSimetricaCifraca;


    public ChaveSimetricaCifrada(String id, String chaveSimetricaCifraca) {
        this.id = id;
        this.chaveSimetricaCifraca = chaveSimetricaCifraca;
    }

    public ChaveSimetricaCifrada() {
    }

    public ChaveSimetricaCifrada(String s) {
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChaveSimetricaCifraca() {
        return chaveSimetricaCifraca;
    }

    public void setChaveSimetricaCifraca(String chaveSimetricaCifraca) {
        this.chaveSimetricaCifraca = chaveSimetricaCifraca;
    }
}
