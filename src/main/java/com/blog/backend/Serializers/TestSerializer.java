package com.blog.backend.Serializers;


import com.blog.backend.entities.React;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.*;

public class TestSerializer extends StdSerializer<Set<React>> {

    public TestSerializer() {
        this(null);
    }

    public TestSerializer(Class<Set<React>> t) {
        super(t);
    }

    @Override
    public void serialize(Set<React> tests, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        List<Map<String, Object>> reacts = new ArrayList<>();
        tests.forEach(t -> {
            reacts.add(new HashMap<>(){
                {
                    put("id", t.getId());
                    put("user",t.getUser().getFirstName());
                    put("post",t.getPost().getPostTitle());
                }
            }) ;
        });
        jsonGenerator.writeObject(reacts);
    }
}
