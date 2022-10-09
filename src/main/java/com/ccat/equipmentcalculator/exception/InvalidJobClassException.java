package com.ccat.equipmentcalculator.exception;

import org.springframework.http.HttpStatus;

public class InvalidJobClassException extends RuntimeException {
    private String message;
    private HttpStatus httpStatus;

    public InvalidJobClassException(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
