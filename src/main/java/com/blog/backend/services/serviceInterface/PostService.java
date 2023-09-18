package com.blog.backend.services.serviceInterface;

import com.blog.backend.controllers.DTOs.PostDTO;
import com.blog.backend.entities.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;


import java.util.List;
import java.util.Optional;

public interface PostService {

   Post addPost(PostDTO postDTO);


   Post sharePost(Integer originalPostId, Integer userWhoWantToShare , PostDTO sharePostDTO);

   ResponseEntity<String> deletePost(Integer postId);

   Post updatePost(Integer postId , PostDTO newPostDTO);

   Optional<Post> getPostByPostId(Integer postId);


   Page<Post> getAllPosts(Pageable pageable);


   Page<Post> getAllPostByUserId(Integer userId, Pageable pageable);

}
