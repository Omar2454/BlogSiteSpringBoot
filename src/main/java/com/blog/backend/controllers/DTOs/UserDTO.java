package com.blog.backend.controllers.DTOs;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    String firstName;
    String lastName;
    String email;
    String password;

}
