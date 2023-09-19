package com.blog.backend.services.serviceInterface;

import com.blog.backend.controllers.DTOs.CommentDTO;
import com.blog.backend.entities.Comment;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CommentService {

    Comment addComment(CommentDTO commentDTO, Integer postId, Integer userId);

    ResponseEntity<String> deleteComment(Integer commentId, Integer userId);

    Comment updateComment(Integer commentId , CommentDTO newCommentDTO);

    Comment getSpecificComment(Integer commentId);




    List<Comment> getAllCommentByPostId(Integer postId);

    Integer getAllCommentByPostIdCount(Integer postId);

}
