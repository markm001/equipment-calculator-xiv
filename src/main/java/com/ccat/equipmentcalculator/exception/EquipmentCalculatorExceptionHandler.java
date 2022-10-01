package com.ccat.equipmentcalculator.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class EquipmentCalculatorExceptionHandler {

    @ExceptionHandler(value=Throwable.class)
    public ResponseEntity<Info> handleErrors(HttpServletRequest request, Throwable e) {
        System.out.println(e.getMessage());
        System.out.println(request.getRequestURI());

        Info info = new Info(e.getMessage().isBlank()? "An Error occurred" : e.getMessage(),
                request.getRequestURI());

        return new ResponseEntity<>(info, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private static class Info {
        private final String message;
        private final String path;

        public Info(String message, String path) {
            this.message = message;
            this.path = path;
        }

        public String getMessage() {
            return message;
        }
        public String getPath() {
            return path;
        }
    }

}
