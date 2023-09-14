package com.blog.backend.controllers.exceptions;

import lombok.Getter;

@Getter
public class GeneralException extends Exception {
    private final ExceptionData exceptionData;

    public GeneralException(ErrorCode code, String msg) {
        exceptionData = new ExceptionData(code.getCode(), msg);
    }
}
