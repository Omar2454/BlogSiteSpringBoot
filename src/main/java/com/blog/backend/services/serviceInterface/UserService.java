package com.blog.backend.services.serviceInterface;

import com.blog.backend.controllers.DTOs.UserDTO;
import com.blog.backend.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {

    User addUser(UserDTO userDTO);
    ResponseEntity<String> deleteUser(Integer userId);

    User updateUser(Integer userId , UserDTO newUserDTO);

    User getSpecificUser(Integer userId);

    Page<User> getAllUser(Pageable pageable);

    ResponseEntity<?> getAllFriends(Integer userId,int page,int size);
}
