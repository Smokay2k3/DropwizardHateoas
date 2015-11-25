package serializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import hateoas.Links;

/**
 * Created by timp on 13/11/2015.
 */
public class LinksSerializer extends JsonSerializer<Links> {

    @Override
    public void serialize(Links value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
        gen.writeObject(value.getLinks());
    }
}
