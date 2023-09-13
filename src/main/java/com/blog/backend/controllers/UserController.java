package com.blog.backend.controllers;

import com.blog.backend.constants.BlogConstants;
import com.blog.backend.controllers.DTOs.UserDTO;
import com.blog.backend.entities.Friendship;
import com.blog.backend.entities.User;
import com.blog.backend.services.serviceInterface.UserService;
import com.blog.backend.utils.BlogUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/blog/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);


    @Autowired
    private UserService userService;




    @PostMapping("add")
     public User addUser(@RequestBody UserDTO userDTO){
       return userService.addUser(userDTO);
    }

    //check
    @DeleteMapping("delete/{user-id}")
    public ResponseEntity<String> deleteUser(@PathVariable("user-id") Integer userId){
        return userService.deleteUser(userId);
    }

    //check


    @PutMapping("update/{user-id}")
    public User updateUser(@PathVariable("user-id") Integer userId , @RequestBody UserDTO newUserDTO){
        return userService.updateUser(userId,newUserDTO);
    }

    //check
    @GetMapping("get/{user-id}")
    public  User getSpecificUser(@PathVariable("user-id") Integer userId){
        return userService.getSpecificUser(userId);

    }



    @GetMapping("get/all")
    public ResponseEntity<Page<User>> getAllUser(Pageable pageable) {
        Page<User> users = userService.getAllUser(pageable);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("get/friends/{userId}")
    public ResponseEntity<?> getAllFriends(@PathVariable("userId") Integer userId,
                                           @RequestParam(name = "page", defaultValue = "0") int page,
                                           @RequestParam(name = "size", defaultValue = "10") int size) {
        try {
            ResponseEntity<?> response = userService.getAllFriends(userId,page,size);

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
}

