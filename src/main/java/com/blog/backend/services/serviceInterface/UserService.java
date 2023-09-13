package com.blog.backend.services.serviceinterface;

import com.blog.backend.controllers.DTOs.UserDTO;
import com.blog.backend.entities.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {

    User addUser(UserDTO userDTO);
    ResponseEntity<String> deleteUser(Integer userId);

    User updateUser(Integer userId , UserDTO newUserDTO);

    User getSpecificUser(Integer userId);

    List<User> getAllUser();
}
