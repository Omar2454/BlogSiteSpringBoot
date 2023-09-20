package com.blog.backend.controllers;

import com.blog.backend.controllers.DTOs.CommentDTO;
import com.blog.backend.entities.Comment;
import com.blog.backend.services.serviceInterface.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    //(naming of the frontend)

    //(done)
    @PostMapping("add-comment/{postID}")
    public Comment addComment(@RequestBody CommentDTO commentDTO, @PathVariable("postID") Integer postId){
         return commentService.addComment(commentDTO, postId);
    }


    //(done)
    @DeleteMapping("delete-comment/{commentId}/{userId}")
    public ResponseEntity<String> deleteComment(@PathVariable("commentId") Integer commentId,@PathVariable("userId") Integer userId) {
return commentService.deleteComment(commentId , userId);

    }


  //(not define by frontend)
    @PutMapping("update/{comment-id}")
    public  Comment updateComment(@PathVariable("comment-id") Integer commentId ,@RequestBody CommentDTO newCommentDTO){
        return commentService.updateComment(commentId,newCommentDTO);

    }

   //(not define by frontend)
    @GetMapping("get/{comment-id}")
    public Comment getSpecificComment(@PathVariable("comment-id") Integer commentId){
       return commentService.getSpecificComment(commentId);
    }



    //(done)

    @GetMapping("all-comment/{postID}")
    public List<Comment> getAllCommentByPostId(@PathVariable("postID") Integer postId){
        return commentService.getAllCommentByPostId(postId);
    }


    //(done)
    @GetMapping("all-comment-count/{postID}")
    public Integer getAllCommentByPostIdCount(@PathVariable("postID") Integer postId) {
    return commentService.getAllCommentByPostIdCount(postId);
    }



    }
