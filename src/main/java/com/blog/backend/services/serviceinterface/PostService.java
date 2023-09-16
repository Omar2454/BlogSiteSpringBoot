package com.blog.backend.services.serviceinterface;

import com.blog.backend.controllers.DTOs.PostDTO;
import com.blog.backend.entities.Post;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface PostService {

   Post addPost(PostDTO postDTO);


   Post sharePost(Integer originalPostId, Integer postToShareId , PostDTO sharePostDTO);

   ResponseEntity<String> deletePost(Integer postId);

   Post updatePost(Integer postId , PostDTO newPostDTO);

   Optional<Post> getPostByPostId(Integer postId);


   List<Post> getAllPosts();


   List<Post> getAllPostByUserId(Integer userId);

}
