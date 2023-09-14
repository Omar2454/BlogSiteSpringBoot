package com.blog.backend.controllers;


import com.blog.backend.constants.BlogConstants;
import com.blog.backend.controllers.exceptions.ErrorCode;
import com.blog.backend.controllers.exceptions.GeneralException;
import com.blog.backend.entities.enums.Reacts;
import com.blog.backend.services.serviceImpl.FriendshipServiceImpl;
import com.blog.backend.services.serviceInterface.ReactsService;
import com.blog.backend.utils.BlogUtils;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Level;

@RestController
@AllArgsConstructor
@RequestMapping("api/blog/post/reacts")
public class ReactsController {
    private final ReactsService reactsService;


    private static final Logger logger = LoggerFactory.getLogger(FriendshipController.class);


    @PostMapping("addOrUpdate/{userId}/{postId}/{react}")
    public ResponseEntity<?> addReactOrChange(@PathVariable("userId") Integer user1Id, @PathVariable("postId") Integer postId ,@PathVariable("react") Reacts react) throws GeneralException {
        try {
            return reactsService.addReact(user1Id,postId,react);
        } catch (Exception e) {
            logger.error("Error Logging user", e);
        }
        throw new GeneralException(ErrorCode.SOMETHING_WENT_WRONG,"Something Went Wrong");
    }
}
