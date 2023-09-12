package com.blog.backend.services.serviceinterface;

import com.blog.backend.controllers.DTOs.CommentDTO;
import com.blog.backend.entities.Comment;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CommentService {

    Comment addComment(CommentDTO commentDTO);
    ResponseEntity<String> deleteComment(Integer commentId);

    Comment updateComment(Integer commentId , CommentDTO newCommentDTO);

    Comment getSpecificComment(Integer commentId);

    List<Comment> getAllComment();

}
