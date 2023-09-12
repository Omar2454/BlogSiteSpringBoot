package com.blog.backend.controllers;

import com.blog.backend.controllers.DTOs.UserDTO;
import com.blog.backend.entities.User;
import com.blog.backend.services.serviceinterface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/blog")
public class UserController {

    @Autowired
    private UserService userService;

    //check
    @PostMapping("add/user")
     public User addUser(@RequestBody UserDTO userDTO){
       return userService.addUser(userDTO);
    }

    //check
    @DeleteMapping("delete/user/{user-id}")
    public ResponseEntity<String> deleteUser(@PathVariable("user-id") Integer userId){
        return userService.deleteUser(userId);
    }


    //check
    @PutMapping("update/user/{user-id}")
    public User updateUser(@PathVariable("user-id") Integer userId , @RequestBody UserDTO newUserDTO){
        return userService.updateUser(userId,newUserDTO);
    }

    //check
    @GetMapping("get/user/{user-id}")
    public  User getSpecificUser(@PathVariable("user-id") Integer userId){
        return userService.getSpecificUser(userId);

    }


    @GetMapping("get/user/all")
    public  List<User> getAllUser(){
        return userService.getAllUser();
    }
}