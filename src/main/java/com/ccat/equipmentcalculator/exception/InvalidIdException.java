package com.ccat.equipmentcalculator.exception;

import org.springframework.http.HttpStatus;

import java.util.function.Supplier;

public class InvalidIdException extends RuntimeException implements Supplier<InvalidIdException> {
    private String message;
    private HttpStatus httpStatus;

    public InvalidIdException(String message, HttpStatus httpStatus) {
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

    @Override
    public InvalidIdException get() {
        return this;
    }
}
