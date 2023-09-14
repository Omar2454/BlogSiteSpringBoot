package com.blog.backend.Serializers;


import com.blog.backend.entities.React;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.*;

public class ReactsSerializer extends StdSerializer<Set<React>> {

    public ReactsSerializer() {
        this(null);
    }

    public ReactsSerializer(Class<Set<React>> t) {
        super(t);
    }

    @Override
    public void serialize(Set<React> reacts, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        List<Map<String, Object>> reactsList = new ArrayList<>();
        reacts.forEach(t -> {
            reactsList.add(new HashMap<>(){
                {
                    put("id", t.getId());
                    put("Emoji",t.getEmoji());
                }
            }) ;
        });
        jsonGenerator.writeObject(reactsList);
    }
}
