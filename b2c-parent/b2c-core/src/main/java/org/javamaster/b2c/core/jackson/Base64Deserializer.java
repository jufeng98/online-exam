package org.javamaster.b2c.core.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.springframework.util.Base64Utils;

import java.io.IOException;

/**
 * @author yudong
 * @date 2019/8/7
 */
public class Base64Deserializer extends JsonDeserializer<byte[]> {
    private static final String PREFIX = "data:image/jpeg;base64,";

    @Override
    public byte[] deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String text = jsonParser.getText();
        text = text.substring(PREFIX.length());
        return Base64Utils.decodeFromString(text);
    }
}
