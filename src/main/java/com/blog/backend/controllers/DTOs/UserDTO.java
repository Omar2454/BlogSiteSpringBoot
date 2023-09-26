package com.blog.backend.controllers.DTOs;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.File;
import java.sql.Blob;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String name;
    private String password;
    private String email;
    private String bio;
    private String phone;
    private String facebookUsername;

    //image Profile
    private String pic;



}
