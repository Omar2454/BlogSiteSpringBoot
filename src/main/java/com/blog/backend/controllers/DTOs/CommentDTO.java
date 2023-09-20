package com.blog.backend.controllers.DTOs;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {
    String commentText;

    int userId;


}
