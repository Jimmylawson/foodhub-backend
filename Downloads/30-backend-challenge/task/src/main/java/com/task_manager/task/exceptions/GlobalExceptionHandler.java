package com.task_manager.task.exceptions;


import com.task_manager.task.dto.ErrorResponseDto;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends RuntimeException{

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleTaskNotFoundException(TaskNotFoundException taskNotFoundException) {
            log.error("User not found  exception occurred {}", taskNotFoundException.getMessage());

            var errorResponse  = new ErrorResponseDto(
                    "Task not found",
                    HttpStatus.NOT_FOUND,
                    taskNotFoundException.getMessage(),
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
                exception.getMessage(),
                LocalDateTime.now()

        );

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    };

    /// Avoid duplicate of Task
    @ExceptionHandler(DuplicateTaskException.class)
    public ResponseEntity<ErrorResponseDto> handleDuplicateTaskException(DuplicateTaskException duplicateTaskException) {
        log.error("Duplicate task exception occurred {}", duplicateTaskException.getMessage());

        var errorResponse = new ErrorResponseDto(
                "Duplicate task",
                HttpStatus.BAD_REQUEST,
                duplicateTaskException.getMessage(),
                LocalDateTime.now()

        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
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
