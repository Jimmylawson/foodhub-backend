package com.jimmydev.blog_api.exceptions;


import com.jimmydev.blog_api.dtos.ErrorResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler  extends RuntimeException{

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handlePostNotFoundException(PostNotFoundException ex, HttpServletRequest request) {
        log.error("Post not found: {}", ex.getMessage());
        
        var errorResponse = new ErrorResponseDto(
                "Post not found",
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                LocalDateTime.now()
        );
        
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleUserNotFoundException(UserNotFoundException userNotFoundException) {
        log.error("User not found  exception occurred {}", userNotFoundException.getMessage());

        var errorResponse  = new ErrorResponseDto(
                "User not found",
                HttpStatus.NOT_FOUND,
                userNotFoundException.getMessage(),
                LocalDateTime.now()

        );

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleException(Exception exception) {
        log.error("Exception occurred {}", exception.getMessage());

        var errorResponse = new ErrorResponseDto(
                "Internal server error",
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Something went wrong",
                LocalDateTime.now()

        );

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    };


    /// Here I will handle method argument exceptions for example with the @Valid
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleValidationExceptions(MethodArgumentNotValidException ex){
        log.error("Validation exception occurred: {} ", ex.getMessage());
        Map<String,String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }

    /// Handles the class level exception such as the @Validated
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, List<String>>> handleConstraintViolation(ConstraintViolationException ex){
        log.error("Validation exception occurred: {} ", ex.getMessage());
        List<String> errors = ex
                .getConstraintViolations()
                .stream()
                .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                .toList();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("errors", errors));
    }

}
