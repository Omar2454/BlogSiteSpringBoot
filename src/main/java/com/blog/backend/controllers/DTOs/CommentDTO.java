package com.blog.backend.controllers.DTOs;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {
    String commentText;
    //foreign //TODO (required in addComment)
    int userId;
    int postId;

}
