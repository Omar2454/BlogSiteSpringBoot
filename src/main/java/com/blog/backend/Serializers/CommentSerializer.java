package com.blog.backend.Serializers;


import com.blog.backend.entities.Comment;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CommentSerializer extends StdSerializer<Set<Comment>> {

    public CommentSerializer() {
        this(null);
    }

    public CommentSerializer(Class<Set<Comment>> t) {
        super(t);
    }

    @Override
    public void serialize(Set<Comment> comments, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        List<Map<String, Object>> commentsList = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"); // Define the desired date-time format

        comments.forEach(comment -> {
            commentsList.add(new HashMap<>() {
                {
                    put("Comment Id", comment.getId());
                    put("Comment Text", comment.getComment_text());
                    put("Comment User Id", comment.getUser().getId());
                    put("Post Id", comment.getPost().getId());
                    put("Created At", comment.getCreatedAt().format(formatter));
                    put("Updated At", comment.getCreatedAt().format(formatter));


                }
            });
        });
        jsonGenerator.writeObject(commentsList);
    }

}
