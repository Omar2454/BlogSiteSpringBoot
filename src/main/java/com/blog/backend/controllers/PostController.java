package com.blog.backend.controllers;

import com.blog.backend.controllers.DTOs.PostDTO;
import com.blog.backend.entities.Post;
import com.blog.backend.services.serviceInterface.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/blog/post")
public class PostController {

    @Autowired
    private PostService postService;



    @PostMapping("add")
    public Post addPost(@RequestBody PostDTO postDTO) {
        return postService.addPost(postDTO);

    }



    @PostMapping("share/{original-post-id}/{user-who-want-to-share}")
    public Post sharePost(@PathVariable("original-post-id") Integer originalPostId,@PathVariable("user-who-want-to-share") Integer userWhoWantToShare , @RequestBody PostDTO sharePostDTO){
    return postService.sharePost(originalPostId , userWhoWantToShare , sharePostDTO);



    
    @DeleteMapping("delete/{post-id}")
    public ResponseEntity<String> deletePost(@PathVariable("post-id") Integer postId) {
        return postService.deletePost(postId);
    }

    
    @PutMapping("update/{post-id}")
    public Post updatePost(@PathVariable("post-id") Integer postId, @RequestBody PostDTO newPostDTO) {
        return postService.updatePost(postId, newPostDTO);
    }


    @GetMapping("get/postByPostId/{post-id}")
    public Optional<Post> getPostByPostId(@PathVariable("post-id") Integer postId) {
        return postService.getPostByPostId(postId);
    }

    @GetMapping("get/postsByUserId/{user-id}")
    public Page<Post> getPostByUserId(@PathVariable("user-id") Integer userId, Pageable pageable) {
        return postService.getAllPostByUserId(userId, pageable);
    }


    @GetMapping("get/all")
    public Page<Post> getAllPost(Pageable pageable) {
        return postService.getAllPosts(pageable);
    }

}
