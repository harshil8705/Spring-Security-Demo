package com.web.Security.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiException> handleUsernameNotFoundException(Exception e) {

        ApiException apiException = ApiException.builder()
                .exception("Username not found with username: " + e.getMessage())
                .httpStatus(HttpStatus.NOT_FOUND)
                .build();

        return new ResponseEntity<>(apiException, apiException.getHttpStatus());

    }

}
