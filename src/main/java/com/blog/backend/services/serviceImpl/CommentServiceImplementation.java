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
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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
                .updatedAt(LocalDateTime.now())
                .user(user)
                .post(post)
                .build();
        return commentRepository.save(comment);
    }

    @Override
    public void deleteComment(Integer commentId) {
        //find Comment by its ID
        commentRepository.findById(commentId).orElseThrow(() -> new EntityNotFoundException("Comment ID not found"));
        commentRepository.deleteById(commentId);
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
        commentRepository.findById(commentId).orElseThrow(() -> new EntityNotFoundException("Comment are not Found"));
        return commentRepository.getReferenceById(commentId);
    }


}
