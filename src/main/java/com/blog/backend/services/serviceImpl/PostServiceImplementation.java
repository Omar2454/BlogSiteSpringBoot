package com.blog.backend.services.serviceImpl;

import com.blog.backend.controllers.DTOs.PostDTO;
import com.blog.backend.entities.Post;
import com.blog.backend.entities.User;
import com.blog.backend.repos.PostRepository;
import com.blog.backend.repos.UserRepository;
import com.blog.backend.services.serviceInterface.PostService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
        Post post = Post.builder().postTitle(postDTO.getPostTitle()).postDescription(postDTO.getPostDescription()).imageBase(postDTO.getImgUrl()).createdAt(LocalDateTime.now()).user(user).build();
        return postRepository.save(post);
    }

    @Override
    public ResponseEntity<String> deletePost(Integer postId) {
        Optional<Post> post = postRepository.findById(postId);
        if (post.isEmpty()) {
            return new ResponseEntity<>("Post ID not found", HttpStatus.BAD_REQUEST);
        } else {
            postRepository.deleteById(postId);
            return new ResponseEntity<>("Post Deleted Successfully", HttpStatus.OK);
        }
    }

    @Override
    public Post updatePost(Integer postId, PostDTO newPostDTO) {
        //find post by its ID
        Post post = postRepository.findById(postId).orElseThrow(() -> new EntityNotFoundException("Post not found"));
        //update (Post)
        post.setPostTitle(newPostDTO.getPostTitle());
        post.setPostDescription(newPostDTO.getPostDescription());
        post.setImageBase(newPostDTO.getImgUrl());
        post.setUpdatedAt(LocalDateTime.now());

        return postRepository.saveAndFlush(post);
    }

    @Override
    public Optional<Post> getPostByPostId(Integer postId) {
        return postRepository.findById(postId);

    }

    public Page<Post> getAllPostByUserId(Integer userId, Pageable pageable) {
        return postRepository.findAllByUserId(userId, pageable);
    }



    @Override
    public Page<Post> getAllPosts(Pageable pageable) {
        return postRepository.findAll(pageable);
    }


}
