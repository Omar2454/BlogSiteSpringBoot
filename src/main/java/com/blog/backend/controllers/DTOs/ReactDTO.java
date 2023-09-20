package com.blog.backend.controllers.DTOs;

import com.blog.backend.entities.enums.Reacts;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReactDTO {
    int postId;
    Reacts react;
}
