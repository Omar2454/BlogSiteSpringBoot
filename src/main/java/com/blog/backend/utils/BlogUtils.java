package com.blog.backend.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class BlogUtils {

    private BlogUtils(){

    }

    public static ResponseEntity<String> getResponseEntity(String responseMessage, HttpStatus httpStatus){
        return new ResponseEntity<String>
                ("{\"message\":\""+responseMessage+"\"}", httpStatus);
    }
}
