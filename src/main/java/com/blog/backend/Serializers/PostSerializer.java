package com.blog.backend.Serializers;


import com.blog.backend.entities.Comment;
import com.blog.backend.entities.Post;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class PostSerializer extends StdSerializer<Set<Post>> {

    public PostSerializer() {
        this(null);
    }

    public PostSerializer(Class<Set<Post>> t) {
        super(t);
    }

    @Override
    public void serialize(Set<Post> posts, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

        List<Map<String, Object>> postsList = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"); // Define the desired date-time format

        posts.forEach(post -> {
            postsList.add(new HashMap<>() {
                {
                    put("Post Id", post.getId());
                }
            });
        });
        jsonGenerator.writeObject(postsList);
    }

}
