package com.blog.backend.controllers;


import com.blog.backend.controllers.DTOs.ReactDTO;
import com.blog.backend.controllers.exceptions.GeneralException;
import com.blog.backend.entities.React;
import com.blog.backend.entities.enums.Reacts;
import com.blog.backend.services.serviceInterface.ReactsService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/post/reacts")
public class ReactsController {
    private final ReactsService reactsService;


    @PostMapping("addOrUpdate/{userId}")
    public ResponseEntity<?> addReactOrChange(@PathVariable("userId") Integer user1Id, @RequestBody ReactDTO reactDTO) throws GeneralException {
        return reactsService.addReact(user1Id, reactDTO);
    }


//
//    @GetMapping("get/all")
//    public List<React> getAllReacts(){
//        return  reactsService.getAllReacts();
//    }

    @GetMapping("get/all/{post-id}")
    public List<React> getAllReactsByPostId(@PathVariable("post-id") Integer postId){
        return reactsService.getAllReactsByPostId(postId);
    }

    @GetMapping("get/count/{post-id}")
    public Integer getReactCountsByPostId(@PathVariable("post-id") Integer postId){
        return reactsService.getReactCountsByPostId(postId);
    }
    @DeleteMapping ("remove/{userId}/{post-id}")
    public ResponseEntity<?> removeReact(@PathVariable("userId") Integer user1Id,@PathVariable("post-id") Integer postId) throws GeneralException {
        return reactsService.removeReactSe(user1Id, postId);
    }
}
