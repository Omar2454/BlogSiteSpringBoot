package com.blog.backend.services.serviceInterface;

import com.blog.backend.controllers.DTOs.ReactDTO;
import com.blog.backend.controllers.exceptions.GeneralException;
import com.blog.backend.entities.React;
import com.blog.backend.entities.enums.Reacts;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ReactsService {
    ResponseEntity<?> addReact(Integer user1Id, ReactDTO reactDTO) throws GeneralException;

    List<React> getAllReactsByPostId(Integer postId);


    Integer getReactCountsByPostId(Integer postId);


    ResponseEntity<?> removeReactSe(Integer user1Id, Integer postId) throws GeneralException;

}
