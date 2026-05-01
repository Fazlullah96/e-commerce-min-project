package com.example.exceptions;

import com.example.error.ErrorMessage;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorMessage> productNotFoundException(
            ProductNotFoundException ex,
            HttpServletRequest request
    ){
        ErrorMessage error = ErrorMessage
                .builder()
                .timeStamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                .path(request.getRequestURI())
                .message(ex.getMessage())
                .build();

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorMessage> userNotFoundException(
            UserNotFoundException ex,
            HttpServletRequest request
    ){
        ErrorMessage error = ErrorMessage
                .builder()
                .timeStamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                .path(request.getRequestURI())
                .message(ex.getMessage())
                .build();

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OutOfStockException.class)
    public ResponseEntity<ErrorMessage> outOfStockException(
            OutOfStockException ex,
            HttpServletRequest request
    ){
        ErrorMessage error = ErrorMessage
                .builder()
                .timeStamp(LocalDateTime.now())
                .status(HttpStatus.INSUFFICIENT_STORAGE.value())
                .error(HttpStatus.INSUFFICIENT_STORAGE.getReasonPhrase())
                .path(request.getRequestURI())
                .message(ex.getMessage())
                .build();

        return new ResponseEntity<>(error, HttpStatus.INSUFFICIENT_STORAGE);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ErrorMessage> orderNotFoundException(
            OrderNotFoundException ex,
            HttpServletRequest request
    ){
        ErrorMessage error = ErrorMessage
                .builder()
                .timeStamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                .path(request.getRequestURI())
                .message(ex.getMessage())
                .build();

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
