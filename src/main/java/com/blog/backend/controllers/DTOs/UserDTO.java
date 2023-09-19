package com.blog.backend.controllers.DTOs;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;
import java.sql.Blob;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String name;
    private String password;
    private String email;
    private String bio;
    private String phoneNumber;
    private String facebookUsername;

    //image Profile
    private String pic;



}
