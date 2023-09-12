package com.blog.backend.services.serviceinterface;

import com.blog.backend.controllers.DTOs.PostDTO;
import com.blog.backend.entities.Post;

import java.util.List;

public interface PostService {

   Post addPost(PostDTO postDTO);

   void deletePost(Integer postId);

   Post updatePost(Integer postId , PostDTO newPostDTO);

   Post getSpecificPost(Integer postId);

}
