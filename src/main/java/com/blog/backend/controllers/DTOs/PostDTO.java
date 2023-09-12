package com.blog.backend.controllers.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {
    String postTitle;
    String postDescription;
    String postUrl;
    //foreign
    int userId;
}
