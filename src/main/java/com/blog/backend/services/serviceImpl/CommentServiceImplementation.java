package com.blog.backend.services.serviceImpl;

import com.blog.backend.controllers.DTOs.CommentDTO;
import com.blog.backend.entities.Comment;
import com.blog.backend.entities.Post;
import com.blog.backend.entities.User;
import com.blog.backend.repos.CommentRepository;
import com.blog.backend.repos.PostRepository;
import com.blog.backend.repos.UserRepository;
import com.blog.backend.services.serviceinterface.CommentService;
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
@Getter
@Setter
public class CommentServiceImplementation implements CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Override
    public Comment addComment(CommentDTO commentDTO) {
        User user = userRepository.findById(commentDTO.getUserId()).orElseThrow(() -> new EntityNotFoundException("Comment ID not Found"));
        Post post = postRepository.findById(commentDTO.getPostId()).orElseThrow(() -> new EntityNotFoundException("Post ID not Found"));
        Comment comment = Comment.builder()
                .comment_text(commentDTO.getCommentText())
                .createdAt(LocalDateTime.now())
                .user(user)
                .post(post)
                .build();
        return commentRepository.save(comment);
    }

    @Override
    public ResponseEntity<String> deleteComment(Integer commentId) {
        //find Comment by its ID
       Optional<Comment> comment =  commentRepository.findById(commentId);
       if(comment.isEmpty()){
           return  new ResponseEntity<>("Comment ID not found" , HttpStatus.BAD_REQUEST);
       }else{
           commentRepository.deleteById(commentId);
           return  new ResponseEntity<>("Comment delete Successful" , HttpStatus.OK);
       }

    }

    @Override
    public Comment updateComment(Integer commentId, CommentDTO newCommentDTO) {
        //find Comment by its ID
        Comment comment  = commentRepository.findById(commentId).orElseThrow(() -> new EntityNotFoundException("Comment not Found"));
        //update Comment values on (CommentDTO)
        comment.setComment_text(newCommentDTO.getCommentText());
        comment.setUpdatedAt(LocalDateTime.now());

        return commentRepository.save(comment);
    }

    @Override
    public Comment getSpecificComment(Integer commentId) {
       return commentRepository.findById(commentId).orElseThrow(() -> new EntityNotFoundException("Comment are not Found"));
    }

    @Override
    public List<Comment> getAllComment() {
        return commentRepository.findAll();
    }

    //TODO get all comment






}
