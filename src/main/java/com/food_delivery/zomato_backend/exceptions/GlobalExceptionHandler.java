package com.food_delivery.zomato_backend.exceptions;


import com.food_delivery.zomato_backend.dtos.error.ErrorResponseDto;
import com.food_delivery.zomato_backend.exceptions.restaurantException.DeliveryNotFoundException;
import com.food_delivery.zomato_backend.exceptions.restaurantException.MenuItemNotFoundException;
import com.food_delivery.zomato_backend.exceptions.orderException.OrderItemNotFoundException;
import com.food_delivery.zomato_backend.exceptions.orderException.OrderNotFoundException;
import com.food_delivery.zomato_backend.exceptions.restaurantException.RestaurantNotFoundException;
import com.food_delivery.zomato_backend.exceptions.users.DuplicateUserException;
import com.food_delivery.zomato_backend.exceptions.users.UserNotFoundException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    private ErrorResponseDto buildErrorResponse(String message, HttpStatus status, String  details){
        return new ErrorResponseDto(
                message,
                status,
                details,
                LocalDateTime.now()
        );
    }

    /// RESTAURANT
    @ExceptionHandler(RestaurantNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleRestaurantNotFoundException(RestaurantNotFoundException ex) {
        log.error("Restaurant not found exception occurred: {}", ex.getMessage());
        var errorResponseDto = buildErrorResponse(
                "Restaurant not found",
                HttpStatus.NOT_FOUND,
                ex.getMessage());

        return new ResponseEntity<>(errorResponseDto, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(DuplicateRestaurantException.class)
    public ResponseEntity<ErrorResponseDto> handleDuplicateRestaurantException(DuplicateUserException ex) {
        log.error("User already exists: {}", ex.getMessage());
        var errorResponseDto = buildErrorResponse(
                "Restaurant duplicate Not found",
                HttpStatus.CONFLICT,
                ex.getMessage());

        return new ResponseEntity<>(errorResponseDto, HttpStatus.CONFLICT);

    }
    /// Payment
    @ExceptionHandler(PaymentNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handlePaymentNotFoundException(PaymentNotFoundException ex) {
        log.error("Payment not found exception occurred: {}", ex.getMessage());
        var errorResponseDto = buildErrorResponse(
                "Payment not found",
                HttpStatus.NOT_FOUND,
                ex.getMessage());

        return new ResponseEntity<>(errorResponseDto, HttpStatus.NOT_FOUND);
    }
    /// ORDER
    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleOrderNotFoundException(OrderNotFoundException ex) {
        log.error("Order not found exception occurred: {}", ex.getMessage());
        var errorResponseDto = buildErrorResponse(
                "Order not found",
                HttpStatus.NOT_FOUND,
                ex.getMessage());

        return new ResponseEntity<>(errorResponseDto, HttpStatus.NOT_FOUND);

    }
    /// MENU

    @ExceptionHandler(MenuItemNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleMenuItemNotFoundException(MenuItemNotFoundException ex) {
        log.error("MenuItem not found exception occurred: {}", ex.getMessage());
        var errorResponseDto = buildErrorResponse(
                "MenuItem not found",
                HttpStatus.NOT_FOUND,
                ex.getMessage());

        return new ResponseEntity<>(errorResponseDto, HttpStatus.NOT_FOUND);

    }
    /// Delivery
    @ExceptionHandler(DeliveryNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleDeliveryNotFoundException(DeliveryNotFoundException ex) {
        log.error("Delivery not found exception occurred: {}", ex.getMessage());
        var errorResponseDto = buildErrorResponse(
                "Delivery not found",
                HttpStatus.NOT_FOUND,
                ex.getMessage());

        return new ResponseEntity<>(errorResponseDto, HttpStatus.NOT_FOUND);

    }

    ///OrderItem

    @ExceptionHandler(OrderItemNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleOrderItemNotFoundException(OrderItemNotFoundException ex) {
        log.error("OrderItem not found exception occurred: {}", ex.getMessage());
        var errorResponseDto = buildErrorResponse(
                "Item not found",
                HttpStatus.NOT_FOUND,
                ex.getMessage());

        return new ResponseEntity<>(errorResponseDto, HttpStatus.NOT_FOUND);

    }

    /// USER
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleUserNotFoundException(UserNotFoundException ex) {
        log.error("User not found exception occurred: {}", ex.getMessage());
        var errorResponseDto = buildErrorResponse(
                "User not found",
                HttpStatus.NOT_FOUND,
                ex.getMessage());

        return new ResponseEntity<>(errorResponseDto, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(DuplicateUserException.class)
    public ResponseEntity<ErrorResponseDto> handlerDuplicateUserException(DuplicateUserException ex) {
        log.error("User already exists: {}", ex.getMessage());
        var errorResponseDto = buildErrorResponse(
                "User  already exists",
                HttpStatus.CONFLICT,
                ex.getMessage()
        );
        return new ResponseEntity<>(errorResponseDto, HttpStatus.CONFLICT);

    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGenericException(Exception ex, WebRequest webRequest) {
        log.error("An exception occurred: {}", ex.getMessage());

        ErrorResponseDto errorResponseDto = buildErrorResponse(
                webRequest.getDescription(false),
                HttpStatus.INTERNAL_SERVER_ERROR,
                ex.getMessage());
        return new ResponseEntity<>(errorResponseDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

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

    /// AUTHENTICATION EXCEPTIONS
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponseDto> handleInvalidaCredentials(InvalidCredentialsException ex) {
        log.error("Invalid credentials exception occurred: {}", ex.getMessage());

        var errorResponseDto = buildErrorResponse(
                "Authentication failed",
                HttpStatus.UNAUTHORIZED,
                ex.getMessage()
        );

        return new ResponseEntity<>(errorResponseDto, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ExceptionJwtException.class)
    public ResponseEntity<ErrorResponseDto> handleExceptionJwtException(ExceptionJwtException ex) {
        log.error("Exception JWT exception occurred: {}", ex.getMessage());

        HttpStatus status = ex.getMessage().toLowerCase().contains("expired")
                ? HttpStatus.UNAUTHORIZED
                : HttpStatus.FORBIDDEN;

        var errorResponseDto = buildErrorResponse(
                "Authentication failed",
                status,
                ex.getMessage()
        );

        return new ResponseEntity<>(
                errorResponseDto,
               status
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseDto> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex) {
        log.error("Malformed JSON request: {}", ex.getMessage());
        return new ResponseEntity<>(
                buildErrorResponse(
                        "Malformed JSON request",
                        HttpStatus.BAD_REQUEST,
                        "Request JSON is malformed"
                ),
                HttpStatus.BAD_REQUEST
        );
    }


}
