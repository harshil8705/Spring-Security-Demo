package com.web.Security.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiException {

    private LocalDateTime timeStamp;
    private String exception;
    private HttpStatus httpStatus;

    public ApiException(String exception, HttpStatus httpStatus) {

        this.timeStamp = LocalDateTime.now();
        this.exception = exception;
        this.httpStatus = httpStatus;

    }

}
