package com.cy.rememeber.Exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApplicationAdvice {

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<?> applicationExceptionHandler(ApplicationException ex){
        return ResponseEntity
            .status(ex.getHttpStatus())
            .body(new ServerExceptionResponse(ex.getCode(), ex.getReason()));
    }

    public record ServerExceptionResponse(String code, String reason){

    }
}