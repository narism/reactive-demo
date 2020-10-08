package com.breakwater.task.exception;

public class BusinessException extends RuntimeException {

    public BusinessException(String errorMessage) {
        super(errorMessage);
    }
}
