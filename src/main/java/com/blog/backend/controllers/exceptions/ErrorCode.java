package com.blog.backend.controllers.exceptions;

import lombok.Getter;

@Getter
public enum ErrorCode {
    INVALID_DIAL_NUMBER("1"),
    REQUIRED_DIAL_NUMBER("2"),
    USER_DOESNT_EXIST("3"),
    POST_DOESNT_EXIST("4"),
    SOMETHING_WENT_WRONG("5"),
    REACT_ALREADY_EXISTS("6"),
    FILE_DELETION_FAILED("7"),
    REACT_DOESNT_EXIST("8"),
    INVALID_PASSWORD("9"),
    INVALID_EMAIL("10")


    ;



    private final String code;
    private ErrorCode(String code) {
        this.code = code;
    }
}
