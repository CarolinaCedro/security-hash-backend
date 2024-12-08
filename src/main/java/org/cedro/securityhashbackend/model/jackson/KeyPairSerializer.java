package org.cedro.securityhashbackend.model.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import java.util.Base64;
import java.security.KeyPair;

public class KeyPairSerializer extends StdSerializer<KeyPair> {

    public KeyPairSerializer() {
        super(KeyPair.class);
    }

    @Override
    public void serialize(KeyPair keyPair, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("publicKey", Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded()));
        gen.writeStringField("privateKey", Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded()));
        gen.writeEndObject();
    }
}
