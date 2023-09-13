package com.blog.backend.Serializers;


import com.blog.backend.entities.Post;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.*;

public class TestSerializer2 extends StdSerializer<Set<Post>> {

    public TestSerializer2() {
        this(null);
    }

    public TestSerializer2(Class<Set<Post>> t) {
        super(t);
    }

    @Override
    public void serialize(Set<Post> tests, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        List<Map<String, Object>> data = new ArrayList<>();
        tests.forEach((t) -> data.add(new HashMap<>() {{
            put("id", t.getId());
            put("first_name", t.getReacts());
        }}));
        jsonGenerator.writeObject(data);
    }
}
