package org.cedro.securityhashbackend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.hibernate.annotations.UuidGenerator;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.Signature;
import java.util.Base64;

@Entity
public class Assinatura {

    @Id
    @UuidGenerator
    private String id;

    @Column(name = "arquivoAssinado", length = 5000)
    private String arquivoAssinado;  // Aqui armazenamos a assinatura codificada em Base64

    public Assinatura(String id, String arquivoAssinado) {
        this.id = id;
        this.arquivoAssinado = arquivoAssinado;
    }

    public static byte[] signData(byte[] data, PrivateKey privateKey) throws Exception {
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(privateKey);
        signature.update(data);
        return signature.sign();
    }


    public Assinatura() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getArquivoAssinado() {
        return arquivoAssinado;
    }

    public void setArquivoAssinado(String arquivoAssinado) {
        this.arquivoAssinado = arquivoAssinado;
    }

    // Método para obter a assinatura em Base64
    public String getArquivoAssinadoBase64() {
        return Base64.getEncoder().encodeToString(arquivoAssinado.getBytes(StandardCharsets.UTF_8));
    }

}
