package com.blog.backend.controllers;

import com.blog.backend.controllers.DTOs.PostDTO;
import com.blog.backend.entities.Post;
import com.blog.backend.services.serviceinterface.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
           return postService.deletePost(postId);
    }

    //check
    @PutMapping("update/post/{post-id}")
   public Post updatePost(@PathVariable("post-id") Integer postId ,@RequestBody PostDTO newPostDTO){
        return postService.updatePost(postId,newPostDTO);
    }


    @GetMapping("get/postByPostId/{post-id}")
   public Optional<Post> getPostByPostId(@PathVariable("post-id") Integer postId){
        return postService.getPostByPostId(postId);
    }

    @GetMapping("get/postByUserId/{user-id}")
    public List<Post> getPostByUserId(@PathVariable("user-id") Integer userId){
        return postService.getAllPostByUserId(userId);
    }




    @GetMapping("get/post/all")
    public List<Post> getAllPost(){
        return postService.getAllPosts();
    }

}
