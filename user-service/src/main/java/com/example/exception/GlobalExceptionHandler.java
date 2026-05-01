package com.example.exception;

import com.example.error.ErrorMessage;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorMessage> userAlreadyExistsException(
            UserAlreadyExistsException ex,
            HttpServletRequest request
    ){
        ErrorMessage error = ErrorMessage
                .builder()
                .timeStamp(LocalDateTime.now())
                .status(HttpStatus.ALREADY_REPORTED.value())
                .error(HttpStatus.ALREADY_REPORTED.getReasonPhrase())
                .path(request.getRequestURI())
                .message(ex.getMessage())
                .build();

        return new ResponseEntity<>(error, HttpStatus.ALREADY_REPORTED);
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
}
