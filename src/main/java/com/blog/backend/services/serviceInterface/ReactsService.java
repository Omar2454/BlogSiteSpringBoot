package com.blog.backend.services.serviceInterface;

import com.blog.backend.controllers.exceptions.GeneralException;
import com.blog.backend.entities.enums.Reacts;
import org.springframework.http.ResponseEntity;

public interface ReactsService {
    ResponseEntity<?> addReact(Integer user1Id, Integer postId, Reacts react) throws GeneralException;

}
