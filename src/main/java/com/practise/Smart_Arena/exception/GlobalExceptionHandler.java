package com.practise.Smart_Arena.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AllExceptions.EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> EntityNotFoundException(AllExceptions.EntityNotFoundException ex) {
        ErrorResponse errorResponse = ErrorResponse.builder(ex, ex.getStatus(), ex.getMessage()).build();
        return new ResponseEntity<>(errorResponse, errorResponse.getStatusCode());
    }

    @ExceptionHandler(AllExceptions.UsernameAlreadyTakenException.class)
    public ResponseEntity<ErrorResponse> UsernameAlreadyTakenException(AllExceptions.UsernameAlreadyTakenException ex) {
        ErrorResponse errorResponse = ErrorResponse.builder(ex, ex.getStatus(), ex.getMessage()).build();
        return new ResponseEntity<>(errorResponse, errorResponse.getStatusCode());
    }

    @ExceptionHandler(AllExceptions.IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> IllegalArgumentException(AllExceptions.IllegalArgumentException ex) {
        ErrorResponse errorResponse = ErrorResponse.builder(ex, ex.getStatus(), ex.getMessage()).build();
        return new ResponseEntity<>(errorResponse, errorResponse.getStatusCode());
    }

    @ExceptionHandler(AllExceptions.ExpiredJwtException.class)
    public ResponseEntity<ErrorResponse> ExpiredJwtException(AllExceptions.ExpiredJwtException ex) {
        ErrorResponse errorResponse = ErrorResponse.builder(ex, ex.getStatus(), ex.getMessage()).build();
        return new ResponseEntity<>(errorResponse, errorResponse.getStatusCode());
    }

    @ExceptionHandler(AllExceptions.MalformedJwtException.class)
    public ResponseEntity<ErrorResponse> MalformedJwtException(AllExceptions.MalformedJwtException ex) {
        ErrorResponse errorResponse = ErrorResponse.builder(ex, ex.getStatus(), ex.getMessage()).build();
        return new ResponseEntity<>(errorResponse, errorResponse.getStatusCode());
    }

    @ExceptionHandler(AllExceptions.HttpResponseException.class)
    public ResponseEntity<ErrorResponse> HttpResponseException(AllExceptions.HttpResponseException ex) {
        ErrorResponse errorResponse = ErrorResponse.builder(ex, ex.getStatus(), ex.getMessage()).build();
        return new ResponseEntity<>(errorResponse, errorResponse.getStatusCode());
    }

    @ExceptionHandler(AllExceptions.IOException.class)
    public ResponseEntity<ErrorResponse> IOException(AllExceptions.IOException ex) {
        ErrorResponse errorResponse = ErrorResponse.builder(ex, ex.getStatus(), ex.getMessage()).build();
        return new ResponseEntity<>(errorResponse, errorResponse.getStatusCode());
    }

    @ExceptionHandler(AllExceptions.DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> DataIntegrityViolationException(AllExceptions.DataIntegrityViolationException ex) {
        ErrorResponse errorResponse = ErrorResponse.builder(ex, ex.getStatus(), ex.getMessage()).build();
        return new ResponseEntity<>(errorResponse, errorResponse.getStatusCode());
    }

    @ExceptionHandler(AllExceptions.InternalServerError.class)
    public ResponseEntity<ErrorResponse> InternalServerError(AllExceptions.InternalServerError ex) {
        ErrorResponse errorResponse = ErrorResponse.builder(ex, ex.getStatus(), ex.getMessage()).build();
        return new ResponseEntity<>(errorResponse, errorResponse.getStatusCode());
    }
}
