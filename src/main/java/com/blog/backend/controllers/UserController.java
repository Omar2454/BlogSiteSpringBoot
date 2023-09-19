package com.blog.backend.controllers;

import com.blog.backend.controllers.DTOs.UserDTO;
import com.blog.backend.controllers.exceptions.GeneralException;
import com.blog.backend.entities.User;
import com.blog.backend.services.serviceInterface.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/blog/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);


    @Autowired
    private UserService userService;





    @DeleteMapping("delete/{user-id}")
    public ResponseEntity<?> deleteUser(@PathVariable("user-id") Integer userId) throws GeneralException {
        return userService.deleteUser(userId);
    }




    @PutMapping("update/{user-id}")
    public ResponseEntity<?> updateUser(@PathVariable("user-id") Integer userId, @RequestBody UserDTO newUserDTO) throws GeneralException {
        return userService.updateUser(userId, newUserDTO);
    }


    @GetMapping("get/{user-id}")
    public User getSpecificUser(@PathVariable("user-id") Integer userId) throws GeneralException {
        return userService.getSpecificUser(userId);

    }


    @GetMapping("get/all")
    public ResponseEntity<Page<User>> getAllUser(Pageable pageable) throws GeneralException {
        Page<User> users = userService.getAllUser(pageable);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }


    @GetMapping("get/friends/{userId}")
    public ResponseEntity<?> getAllFriends(@PathVariable("userId") Integer userId, Pageable pageable) {
        try {
            ResponseEntity<?> response = userService.getAllFriends(userId, pageable);

            if (response.getStatusCode() == HttpStatus.OK) {
                return response;
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
            }

        } catch (Exception e) {
            logger.error("Error Logging user", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }



    @PutMapping("updateImage/{user-id}")
    public ResponseEntity<?> updateImageByUserId(@PathVariable("user-id") Integer userId, @RequestBody UserDTO newImage){
        System.out.println(newImage);
        return userService.updateImageByUserId(userId, newImage);
    }

}

