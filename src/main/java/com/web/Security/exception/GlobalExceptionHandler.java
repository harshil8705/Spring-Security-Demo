package com.web.Security.exception;

import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;

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

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiException> handleAuthenticationException(Exception e) {

        ApiException apiException = ApiException.builder()
                .exception("Authentication Failed: " + e.getMessage())
                .httpStatus(HttpStatus.UNAUTHORIZED)
                .build();

        return new ResponseEntity<>(apiException, apiException.getHttpStatus());

    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ApiException> handleJwtException(Exception e) {

        ApiException apiException = ApiException.builder()
                .exception("Invalid Jwt Token: " + e.getMessage())
                .httpStatus(HttpStatus.UNAUTHORIZED)
                .build();

        return new ResponseEntity<>(apiException, apiException.getHttpStatus());

    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiException> handleAccessDeniedException(Exception e) {

        ApiException apiException = ApiException.builder()
                .exception("Access denied: Insufficient Permissions.")
                .httpStatus(HttpStatus.FORBIDDEN)
                .build();

        return new ResponseEntity<>(apiException, apiException.getHttpStatus());

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiException> handleException(Exception e) {

        ApiException apiException = ApiException.builder()
                .exception("An unexpected error occurred: " + e.getMessage())
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();

        return new ResponseEntity<>(apiException, apiException.getHttpStatus());

    }

}
