package com.blog.backend.controllers;

import com.blog.backend.constants.BlogConstants;
import com.blog.backend.controllers.DTOs.AuthenticationRequest;
import com.blog.backend.controllers.DTOs.UserDTO;
import com.blog.backend.security.responses.AuthenticationResponse;
import com.blog.backend.services.serviceImpl.auth.AuthenticationService;
import com.blog.backend.utils.BlogUtils;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:4200")
@RestController
@AllArgsConstructor
@RequestMapping("api/outsiders")
public class OutsidersController {
    private final AuthenticationService authenticationService;



    private static final Logger logger = LoggerFactory.getLogger(OutsidersController.class);




    @PostMapping("authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        try {
            return authenticationService.authenticate(request);
        } catch (Exception e) {
            logger.error("Error Logging user", e);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @PostMapping("register")
    public ResponseEntity<?> register(@RequestBody UserDTO userDTO) {
        try {
            return authenticationService.register(userDTO);
        } catch (Exception ex) {
            logger.error("Error registering user", ex);
        }
        return BlogUtils.getResponseEntity(BlogConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);

    }










}
