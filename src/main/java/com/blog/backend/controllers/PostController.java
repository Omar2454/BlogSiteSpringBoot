package com.blog.backend.controllers;

import com.blog.backend.controllers.DTOs.PostDTO;
import com.blog.backend.controllers.exceptions.GeneralException;
import com.blog.backend.entities.Post;
import com.blog.backend.services.serviceInterface.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/post")
public class PostController {

    @Autowired
    private PostService postService;



    //(done)
    @PostMapping("add-post/{userId}")
    public Post addPost(@RequestBody PostDTO postDTO , @PathVariable("userId") Integer userId) {
        return postService.addPost(postDTO,userId);

    }

    @PostMapping("share/{user-who-want-to-share}/{original-post-id}")
    public Post sharePost(@PathVariable("original-post-id") Integer originalPostId,@PathVariable("user-who-want-to-share") Integer userWhoWantToShare , @RequestBody PostDTO sharePostDTO) {
        return postService.sharePost(originalPostId, userWhoWantToShare, sharePostDTO);
    }



    
    @DeleteMapping("delete-post/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable("postId") Integer postId) {
        return postService.deletePost(postId);
    }

    
    @PutMapping("update-post/{postId}")
    public Post updatePost(@PathVariable("postId") Integer postId, @RequestBody PostDTO newPostDTO) {
        return postService.updatePost(postId, newPostDTO);
    }


    @GetMapping("get/postByPostId/{post-id}")
    public Optional<Post> getPostByPostId(@PathVariable("post-id") Integer postId) {
        return postService.getPostByPostId(postId);
    }

    @GetMapping("get/postsByUserId/{visitor-id}/{user-id}")
    public Page<Post> getPostByUserId(@PathVariable("visitor-id") Integer visitorId,@PathVariable("user-id") Integer userId, Pageable pageable) throws GeneralException {
        return postService.getAllPostByUserId(visitorId, userId, pageable);
    }


    @GetMapping("all-posts/{user-id}")
    public Page<Post> getAllPost(Pageable pageable,@PathVariable("user-id") Integer userId) throws GeneralException {
        return postService.getAllPosts(pageable,userId);
    }


    //(done)
//    @GetMapping("posts/{userId}")
//    public Page<Post> getAllPostByUserId(@PathVariable("userId") Integer userId, Pageable pageable){
//        return postService.getAllPostByUserId(userId,pageable);
//    }


}
