package com.blog.backend.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class BlogUtils {

    private BlogUtils(){

    }

    public static ResponseEntity<String> getResponseEntityWithDecision(String responseMessage , boolean decision, HttpStatus httpStatus){
        return new ResponseEntity<String>
                ("{\n \"message\":\""+responseMessage+"\", \n \"status\":\""+decision+"\"\n}", httpStatus);
    }


    public static ResponseEntity<String> getResponseEntity(String responseMessage, HttpStatus httpStatus){
        return new ResponseEntity<String>
                ("{\n \"message\":\""+responseMessage+"\" \n}", httpStatus);
    }
}
