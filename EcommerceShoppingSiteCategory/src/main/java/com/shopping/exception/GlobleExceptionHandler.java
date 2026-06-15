package com.shopping.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobleExceptionHandler {

    @ExceptionHandler(value = ProductNotFoundException.class)
    public ResponseEntity<ApiError> ProductNotFoundExceptionHandler(
            ProductNotFoundException e,
            HttpServletRequest request) {

        ApiError error = new ApiError();
        error.setStatuscode(HttpStatus.NOT_FOUND.value());
        error.setStatusmessage(HttpStatus.NOT_FOUND);
        error.setMessage(e.getMessage());
        error.setDate(new Date());
        error.setUrl(request.getRequestURI());

        return new ResponseEntity<ApiError>(error, HttpStatus.NOT_FOUND);
    }
}
