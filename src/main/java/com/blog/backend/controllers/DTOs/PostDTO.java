package com.blog.backend.controllers.DTOs;

import com.blog.backend.entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {
    String title;
    String content;
    String image;
    User user;
    LocalDateTime createdAt;
    int numberOfReact;
    int numberOfComment;
    int isReact;

    //foreign
    int userId;
}
