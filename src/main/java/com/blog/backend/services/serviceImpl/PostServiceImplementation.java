package com.blog.backend.services.serviceImpl;

import com.blog.backend.controllers.DTOs.PostDTO;
import com.blog.backend.entities.Post;
import com.blog.backend.entities.User;
import com.blog.backend.repos.PostRepository;
import com.blog.backend.repos.UserRepository;
import com.blog.backend.services.serviceinterface.PostService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Setter
@Getter
public class PostServiceImplementation implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    @Override
    public Post addPost(PostDTO postDTO) {
        User user = userRepository.findById(postDTO.getUserId()).orElseThrow(() -> new EntityNotFoundException("User Not found"));
        Post post =  Post.builder()
                .postTitle(postDTO.getPostTitle())
                .postDescription(postDTO.getPostDescription())
                .postUrl(postDTO.getPostUrl())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .user(user)
                .build();
        return postRepository.save(post);
    }

    @Override
    public ResponseEntity<String> deletePost(Integer postId) {
         Optional<Post> post = postRepository.findById(postId);
         if(post.isEmpty()){
             return  new ResponseEntity<>("Post ID not found" , HttpStatus.BAD_REQUEST);
         }else{
             postRepository.deleteById(postId);
return new ResponseEntity<>("Post Deleted Successfully" , HttpStatus.OK);
         }
    }

    @Override
    public Post updatePost(Integer postId, PostDTO newPostDTO) {
        //find post by its ID
        Post post = postRepository.findById(postId).orElseThrow(() -> new EntityNotFoundException("Post not updated"));
        //update (PostDTO)
        post.setPostTitle(newPostDTO.getPostTitle());
        post.setPostDescription(newPostDTO.getPostDescription());
        post.setPostUrl(newPostDTO.getPostUrl());
        post.setUpdatedAt(LocalDateTime.now());

        return postRepository.saveAndFlush(post);
    }

    @Override
    public Optional<Post> getPostByPostId(Integer postId) {
        return postRepository.findById(postId);

    }

    public List<Post> getAllPostByUserId(Integer userId){
        return postRepository.findAllByUserId(userId);
    }


    //TODO get all post
    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }





}