package com.blog.backend.services.serviceInterface;

import com.blog.backend.controllers.DTOs.UserDTO;
import com.blog.backend.controllers.exceptions.GeneralException;
import com.blog.backend.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {


    ResponseEntity<?> deleteUser(Integer userId) throws GeneralException;

    ResponseEntity<?> updateUser(Integer userId , UserDTO newUserDTO) throws GeneralException;

    ResponseEntity<?> updateImageByUserId(Integer userId , UserDTO newImage);

    User getSpecificUser(Integer userId) throws GeneralException;

    Page<User> getAllUser(Pageable pageable) throws GeneralException;

    ResponseEntity<?> getAllFriends(Integer userId,Pageable pageable) throws GeneralException;




}
