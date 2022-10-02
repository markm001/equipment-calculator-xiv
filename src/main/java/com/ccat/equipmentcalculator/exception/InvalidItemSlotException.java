package com.ccat.equipmentcalculator.exception;

import org.springframework.http.HttpStatus;

public class InvalidItemSlotException extends RuntimeException {
    private String message;
    private HttpStatus httpStatus;

    public InvalidItemSlotException(String message, HttpStatus httpStatus) {
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
