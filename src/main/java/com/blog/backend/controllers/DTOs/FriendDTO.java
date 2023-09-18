package com.blog.backend.controllers.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FriendDTO {
    private Integer id;
    private String name;
    private String pic;
}
