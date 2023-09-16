package com.blog.backend.controllers;


import com.blog.backend.controllers.exceptions.GeneralException;
import com.blog.backend.entities.enums.Reacts;
import com.blog.backend.services.serviceInterface.ReactsService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("api/blog/post/reacts")
public class ReactsController {
    private final ReactsService reactsService;


    @PostMapping("addOrUpdate/{userId}/{postId}/{react}")
    public ResponseEntity<?> addReactOrChange(@PathVariable("userId") Integer user1Id, @PathVariable("postId") Integer postId, @PathVariable("react") Reacts react) throws GeneralException {
        return reactsService.addReact(user1Id, postId, react);
    }
}
