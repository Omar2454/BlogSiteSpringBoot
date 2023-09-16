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
import lombok.Setter;
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
                .imageBase(postDTO.getImgUrl())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .user(user)
                .build();
        return postRepository.save(post);
    }

    @Override
    public Post sharePost(Integer originalPostId, Integer postToShareId, PostDTO sharePostDTO) {
        // Find the original post by its ID
        Optional<Post> originalPostOptional = postRepository.findById(originalPostId);
        if (originalPostOptional.isEmpty()) {
            throw new EntityNotFoundException("Original Post not found");
        }
        Post originalPost = originalPostOptional.get();

        // Find the user who is sharing the post
        User user = userRepository.findById(postToShareId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        // Create a new post for sharing based on the original post
        Post sharedPost = Post.builder()
                .postTitle(sharePostDTO.getPostTitle())  // Use the original post's title
                .postDescription(sharePostDTO.getPostDescription())  // Use the original post's description
                .imageBase(originalPost.getImageBase())  // Use the original post's image
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .user(user)  // Set the user who is sharing the post
                .sharePost(originalPost)  // Set the original post being shared
                .build();

        return postRepository.save(sharedPost);
    }







//    @Override
//    public ResponseEntity<String> deletePost(Integer postId) {
//         Optional<Post> post = postRepository.findById(postId);
//         if(post.isEmpty()){
//             return  new ResponseEntity<>("Post ID not found" , HttpStatus.BAD_REQUEST);
//         }else{
//             postRepository.deleteById(postId);
//return new ResponseEntity<>("Post Deleted Successfully" , HttpStatus.OK);
//         }
//    }

    @Override
    public ResponseEntity<String> deletePost(Integer postId) {
        Optional<Post> postOptional = postRepository.findById(postId);
        if (postOptional.isEmpty()) {
            return new ResponseEntity<>("Post ID not found", HttpStatus.BAD_REQUEST);
        } else {
            Post post = postOptional.get();

            // Check if the post is an original post or a shared post
            if (post.getSharePost() != null) {
                // It's a shared post, so delete the shared post only
                postRepository.deleteById(postId);
                return new ResponseEntity<>("Shared Post Deleted Successfully", HttpStatus.OK);
            } else {
                // It's an original post, so delete both the original and shared posts
                // Find and delete the shared posts that reference the original post
                List<Post> sharedPosts = postRepository.findBySharePost(post);
                postRepository.deleteAll(sharedPosts);

                // Delete the original post
                postRepository.deleteById(postId);

                return new ResponseEntity<>("Original Post and Shared Posts Deleted Successfully", HttpStatus.OK);
            }
        }
    }

//    @Override
//    public Post updatePost(Integer postId, PostDTO newPostDTO) {
//        //find post by its ID
//        Post post = postRepository.findById(postId).orElseThrow(() -> new EntityNotFoundException("Post not updated"));
//        //update (PostDTO)
//        post.setPostTitle(newPostDTO.getPostTitle());
//        post.setPostDescription(newPostDTO.getPostDescription());
//        post.setImageBase(newPostDTO.getImgUrl());
//        post.setUpdatedAt(LocalDateTime.now());
//
//        return postRepository.saveAndFlush(post);
//    }

    @Override
    public Post updatePost(Integer postId, PostDTO newPostDTO) {
        // Find the post by its ID
        Optional<Post> postOptional = postRepository.findById(postId);
        if (postOptional.isEmpty()) {
            throw new EntityNotFoundException("Post not found");
        }

        Post post = postOptional.get();

        // Check if the post is an original post or a shared post
        if (post.getSharePost() != null) {
            // It's a shared post, so update the shared post only
            updateSharedPost(post, newPostDTO);
            return postRepository.saveAndFlush(post);
        } else {
            // It's an original post, so update both the original and shared posts
            updateOriginalPostAndSharedPosts(post, newPostDTO);
            return postRepository.saveAndFlush(post);
        }
    }

    // Helper method to update a shared post
    private void updateSharedPost(Post sharedPost, PostDTO newPostDTO) {
        sharedPost.setPostTitle(newPostDTO.getPostTitle());
        sharedPost.setPostDescription(newPostDTO.getPostDescription());
        sharedPost.setImageBase(newPostDTO.getImgUrl());
        sharedPost.setUpdatedAt(LocalDateTime.now());
    }

    // Helper method to update an original post and its shared posts
    private void updateOriginalPostAndSharedPosts(Post originalPost, PostDTO newPostDTO) {
        // Update the original post
        originalPost.setPostTitle(newPostDTO.getPostTitle());
        originalPost.setPostDescription(newPostDTO.getPostDescription());
        originalPost.setImageBase(newPostDTO.getImgUrl());
        originalPost.setUpdatedAt(LocalDateTime.now());

        // Update the shared posts (if any)    //(Custom Query)
        List<Post> sharedPosts = postRepository.findBySharePost(originalPost);
        for (Post sharedPost : sharedPosts) {
            updateSharedPost(sharedPost, newPostDTO);
        }
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
