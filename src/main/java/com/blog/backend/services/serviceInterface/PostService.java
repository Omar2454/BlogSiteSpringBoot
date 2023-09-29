package com.blog.backend.services.serviceInterface;

import com.blog.backend.controllers.DTOs.PostDTO;
import com.blog.backend.controllers.exceptions.GeneralException;
import com.blog.backend.entities.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;


import java.util.List;
import java.util.Optional;

public interface PostService {

   ResponseEntity<?> addPost(PostDTO postDTO , Integer userId);


   ResponseEntity<?> sharePost(Integer originalPostId, Integer userWhoWantToShare , PostDTO sharePostDTO);

   ResponseEntity<?> deletePost(Integer postId);

   ResponseEntity<?> updatePost(Integer postId , PostDTO newPostDTO);

   ResponseEntity<?> getPostByPostId(Integer postId);


   ResponseEntity<?> getAllPosts(Pageable pageable,Integer userId) throws GeneralException;

   Integer getPostCount();


   ResponseEntity<?> getAllPostByUserId(Integer visitorId,Integer userId, Pageable pageable) throws GeneralException;




}
