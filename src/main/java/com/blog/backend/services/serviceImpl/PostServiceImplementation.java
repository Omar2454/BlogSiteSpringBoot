package com.blog.backend.services.serviceImpl;

import com.blog.backend.controllers.DTOs.PostDTO;
import com.blog.backend.controllers.exceptions.ErrorCode;
import com.blog.backend.controllers.exceptions.GeneralException;
import com.blog.backend.entities.Friendship;
import com.blog.backend.entities.Post;
import com.blog.backend.entities.React;
import com.blog.backend.entities.User;
import com.blog.backend.entities.enums.Privacy;
import com.blog.backend.repos.PostRepository;
import com.blog.backend.repos.UserRepository;
import com.blog.backend.services.serviceInterface.PostService;
import com.blog.backend.utils.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
@Setter
@Getter
public class PostServiceImplementation implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;


    //(for future after intern)
    @Override
    public ResponseEntity<?> addPost(PostDTO postDTO, Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User Not found"));
        Post post = Post.builder().postTitle(postDTO.getTitle()).postDescription(postDTO.getContent()).privacy(postDTO.getPrivacy())
                .imageBase(postDTO.getImage())
                .createdAt(LocalDateTime.now())
                .user(user)
                .build();
        ApiResponse apiResponse;
        apiResponse = new ApiResponse(true, "Post Created Successfully");
        apiResponse.setData("post", postRepository.save(post));
        return ResponseEntity.ok().body(apiResponse.getResponse());

    }


    @Override                                       //(userId who share)
    public ResponseEntity<?> sharePost(Integer originalPostId, Integer userWhoWantToShare, PostDTO sharePostDTO) {
        // Find the original post by its ID
        Optional<Post> originalPostOptional = postRepository.findById(originalPostId);
        if (originalPostOptional.isEmpty()) {
            throw new EntityNotFoundException("Original Post not found");
        }
        Post originalPost = originalPostOptional.get();

        // Find the user who is sharing the post
        User user = userRepository.findById(userWhoWantToShare).orElseThrow(() -> new EntityNotFoundException("User not found"));


        Post sharedPostReference = originalPost.getSharePost();
        if (sharedPostReference != null) {
            // The original post has already been shared, so we should reference the original post's ID
            //take the original post ID
            originalPost = sharedPostReference;
        }

        Post sharedPost = Post.builder().postTitle(sharePostDTO.getTitle())  //which you are typing it in request body
                .postDescription(sharePostDTO.getContent())  //which you are typing it in request body
                .imageBase(originalPost.getImageBase())  // Use the original post's image
                .createdAt(LocalDateTime.now()).user(user)  // Set the user id who is sharing the post
                .sharePost(originalPost)  // Set the (modified in title and description) original post being shared
                .build();

        ApiResponse apiResponse;
        apiResponse = new ApiResponse(true, "Post Shared Successfully");
        apiResponse.setData("post", postRepository.save(sharedPost));
        return ResponseEntity.ok().body(apiResponse.getResponse());
    }

    @Override
    public ResponseEntity<?> deletePost(Integer postId) {
        ApiResponse apiResponse;
        Optional<Post> postOptional = postRepository.findById(postId);
        if (postOptional.isEmpty()) {
            apiResponse = new ApiResponse(false, "Post ID not found");
            return ResponseEntity.badRequest().body(apiResponse.getResponse());
        } else {
            Post post = postOptional.get();

            // Check if the post is an original post or a shared post
            if (post.getSharePost() != null) {
                // It's a shared post, so delete the shared post only
                postRepository.deleteById(postId);
                apiResponse = new ApiResponse(true, "Shared Post Deleted Successfully");
            } else {
                // It's an original post, so delete both the original and shared posts
                // Find and delete the shared posts that reference the original post
                List<Post> sharedPosts = postRepository.findBySharePost(post);


                postRepository.deleteAll(sharedPosts);


                // Delete the original post
                postRepository.deleteById(postId);

                apiResponse = new ApiResponse(true, "Original Post and Shared Posts Deleted Successfully");
            }
            return ResponseEntity.ok().body(apiResponse.getResponse());
        }
    }


    @Override
    public ResponseEntity<?> updatePost(Integer postId, PostDTO newPostDTO) {
        // Find the post by its ID
        Optional<Post> postOptional = postRepository.findById(postId);
        if (postOptional.isEmpty()) {
            throw new EntityNotFoundException("Post not found");
        }

        Post post = postOptional.get();
        ApiResponse apiResponse;
        // Check if the post is an original post or a shared post
        if (post.getSharePost() != null) {
            // It's a shared post, so update the shared post only
            updateSharedPost(post, newPostDTO);

            apiResponse = new ApiResponse(true, "Post Updated Successfully");
            apiResponse.setData("post", postRepository.saveAndFlush(post));
            return ResponseEntity.ok().body(apiResponse.getResponse());
        } else {
            // It's an original post, so update both the original and shared posts
            updateOriginalPostAndSharedPosts(post, newPostDTO);

            apiResponse = new ApiResponse(true, "Post Updated Successfully");
            apiResponse.setData("post", postRepository.saveAndFlush(post));
            return ResponseEntity.ok().body(apiResponse.getResponse());

        }
    }

    // Helper method to update a shared post
    private void updateSharedPost(Post sharedPost, PostDTO newPostDTO) {
        sharedPost.setPostTitle(newPostDTO.getTitle());
        sharedPost.setPostDescription(newPostDTO.getContent());
        sharedPost.setImageBase(newPostDTO.getImage());
        sharedPost.setUpdatedAt(LocalDateTime.now());
    }

    // Helper method to update an original post and its shared posts
    private void updateOriginalPostAndSharedPosts(Post originalPost, PostDTO newPostDTO) {
        // Update the original post
        originalPost.setPostTitle(newPostDTO.getTitle());
        originalPost.setPostDescription(newPostDTO.getContent());
        originalPost.setImageBase(newPostDTO.getImage());
        originalPost.setUpdatedAt(LocalDateTime.now());

        // Update the shared posts (if any)
        // (Custom Query)
        List<Post> sharedPosts = postRepository.findBySharePost(originalPost);
        for (Post sharedPost : sharedPosts) {
            updateSharedPost(sharedPost, newPostDTO);
        }
    }

    @Override
    public ResponseEntity<?> getPostByPostId(Integer postId) {
        ApiResponse apiResponse;
        apiResponse = new ApiResponse(true, "Post Retrieved Successfully");
        apiResponse.setData("post", postRepository.findById(postId));
        return ResponseEntity.ok().body(apiResponse.getResponse());


    }

    public ResponseEntity<?> getAllPostByUserId(Integer visitedId, Integer userId, Pageable pageable) throws GeneralException {
        ApiResponse apiResponse;
        if (visitedId.equals(userId)) {
            apiResponse = new ApiResponse(true, "All posts returned successfully");
            Page<Post> posts = postRepository.findAllByUserId(userId, pageable);
            apiResponse.setData("posts", posts);
            return ResponseEntity.ok().body(apiResponse.getResponse());

        } else {
            User user = userRepository.findById(visitedId).orElseThrow(() -> new GeneralException(ErrorCode.USER_DOESNT_EXIST, "User does not exist"));
            Set<Friendship> friendships = user.getFriendships1();
            List<Integer> friendsIds = new ArrayList<>();
            List<Post> allPosts = new ArrayList<>();
            if (!friendships.isEmpty()) {
                for (Friendship friendship : friendships) {
                    friendsIds.add(friendship.getFriendId());
                }
                if (friendsIds.contains(userId)) {

                    allPosts.addAll(postRepository.findPostsByUserAndPrivacy(user, Privacy.FRIENDS));
                }
            }
            List<Post> publicPosts = postRepository.findPostsByUserAndPrivacy(user, Privacy.PUBLIC);
            allPosts.addAll(publicPosts);
            setIsReact(userId, allPosts);
            int pageSize = pageable.getPageSize();
            int currentPage = pageable.getPageNumber();
            int startItem = currentPage * pageSize;

            List<Post> pagedAllPosts;

            if (allPosts.size() < startItem) {
                pagedAllPosts = List.of(); // Return an empty list for out of bounds pages
            } else {
                int toIndex = Math.min(startItem + pageSize, allPosts.size());
                pagedAllPosts = allPosts.subList(startItem, toIndex);
            }


            apiResponse = new ApiResponse(true, "All posts returned successfully");
            apiResponse.setData("posts", new PageImpl<>(pagedAllPosts, PageRequest.of(currentPage, pageSize), allPosts.size()));
            return ResponseEntity.ok().body(apiResponse.getResponse());

        }

    }


    @Override
    public ResponseEntity<?> getAllPosts(Pageable pageable, Integer userId) throws GeneralException {
        User user = userRepository.findById(userId).orElseThrow(() -> new GeneralException(ErrorCode.USER_DOESNT_EXIST, "User does not exist"));
        Set<Friendship> friendships = user.getFriendships1();
        List<Integer> friendsIds = new ArrayList<>();
        List<Post> allPosts = new ArrayList<>();
        if (!friendships.isEmpty()) {
            for (Friendship friendship : friendships) {
                friendsIds.add(friendship.getFriendId());
            }
            for (Integer friendId : friendsIds) {
                List<Post> posts = postRepository.findAllByUserId(friendId);
                for (Post post : posts) {
                    if (post.getPrivacy().equals(Privacy.FRIENDS)) {
                        allPosts.add(post);//friends posts
                    }
                }
            }
        }


        List<Post> publicPosts = postRepository.findPostsByPrivacyIs(Privacy.PUBLIC);

        allPosts.addAll(postRepository.findAllByUserId(userId));//private posts
        allPosts.addAll(publicPosts); //public posts
        setIsReact(userId, allPosts);
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;

        List<Post> pagedAllPosts;

        if (allPosts.size() < startItem) {
            pagedAllPosts = List.of(); // Return an empty list for out of bounds pages
        } else {
            int toIndex = Math.min(startItem + pageSize, allPosts.size());
            pagedAllPosts = allPosts.subList(startItem, toIndex);
        }
        ApiResponse apiResponse;
        apiResponse = new ApiResponse(true, "Post Retrieved Successfully");
        apiResponse.setData("posts", new PageImpl<>(pagedAllPosts, PageRequest.of(currentPage, pageSize), allPosts.size()));
        return ResponseEntity.ok().body(apiResponse.getResponse());


    }

    private static void setIsReact(Integer userId, List<Post> allPosts) {
        for (Post post : allPosts) {
            Set<React> reacts = post.getReacts();
            for (React react : reacts) {
                if (react.getUser().getId().equals(userId)) {
                    post.setIsReact(react.getEmoji().ordinal());
                }
            }
        }
    }

    @Override
    public Integer getPostCount() {
        return postRepository.findAll().size();
    }


}
