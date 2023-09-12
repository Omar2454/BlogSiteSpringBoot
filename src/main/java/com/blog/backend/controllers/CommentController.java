package com.blog.backend.controllers;

import com.blog.backend.controllers.DTOs.CommentDTO;
import com.blog.backend.entities.Comment;
import com.blog.backend.services.serviceinterface.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/blog")
public class CommentController {
    @Autowired
    private CommentService commentService;

    //check
    @PostMapping("add/comment")
    public Comment addComment(@RequestBody CommentDTO commentDTO){
         return commentService.addComment(commentDTO);
    }

    //check
    @DeleteMapping("delete/comment/{comment-id}")
    public ResponseEntity<String> deleteComment(@PathVariable("comment-id") Integer commentId){
        commentService.deleteComment(commentId);
        return new ResponseEntity<>("Deleted Successfully", HttpStatus.OK);
    }


    //check
    @PutMapping("update/comment/{comment-id}")
    public  Comment updateComment(@PathVariable("comment-id") Integer commentId ,@RequestBody CommentDTO newCommentDTO){
        return commentService.updateComment(commentId,newCommentDTO);

    }

    //check
    @GetMapping("get/comment/{comment-id}")
    public Comment getSpecificComment(@PathVariable("comment-id") Integer commentId){
       return commentService.getSpecificComment(commentId);
    }


}
