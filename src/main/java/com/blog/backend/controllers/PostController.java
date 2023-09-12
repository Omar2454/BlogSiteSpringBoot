package com.blog.backend.controllers;

import com.blog.backend.controllers.DTOs.PostDTO;
import com.blog.backend.entities.Post;
import com.blog.backend.services.serviceinterface.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/blog")
public class PostController {

    @Autowired
    private PostService postService;

    //check
    @PostMapping("add/post")
    public Post addPost(@RequestBody PostDTO postDTO){
       return postService.addPost(postDTO);

    }
    //check
    @DeleteMapping("delete/post/{post-id}")
    public ResponseEntity<String> deletePost(@PathVariable("post-id") Integer postId){
        postService.deletePost(postId);
        return new ResponseEntity<>("Deleted Successfully", HttpStatus.OK);

    }

    //check
    @PutMapping("update/post/{post-id}")
   public Post updatePost(@PathVariable("post-id") Integer postId ,@RequestBody PostDTO newPostDTO){
        return postService.updatePost(postId,newPostDTO);
    }

    //check
    @GetMapping("get/post/{post-id}")
   public Post getSpecificPost(@PathVariable("post-id") Integer postId){
        return postService.getSpecificPost(postId);
    }

}
