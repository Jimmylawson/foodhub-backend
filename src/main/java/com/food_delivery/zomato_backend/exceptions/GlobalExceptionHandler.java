package com.food_delivery.zomato_backend.exceptions;


import com.food_delivery.zomato_backend.dtos.error.ErrorResponseDto;
import com.food_delivery.zomato_backend.exceptions.DeliveryExcpetion.DeliveryNotFoundException;
import com.food_delivery.zomato_backend.exceptions.MenuItemException.MenuItemNotFoundException;
import com.food_delivery.zomato_backend.exceptions.orderException.OrderNotFoundException;
import com.food_delivery.zomato_backend.exceptions.restaurantException.RestaurantNotFoundException;
import com.food_delivery.zomato_backend.exceptions.users.DuplicateUserException;
import com.food_delivery.zomato_backend.exceptions.users.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /// RESTAURANT
    @ExceptionHandler(RestaurantNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleRestaurantNotFoundException(RestaurantNotFoundException ex) {
        log.error("Restaurant not found exception occurred: {}", ex.getMessage());
        var errorResponseDto = new ErrorResponseDto(
                "Restaurant not found",
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                LocalDateTime.now());

        return new ResponseEntity<>(errorResponseDto, HttpStatus.NOT_FOUND);

    }

    /// ORDER
    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleOrderNotFoundException(OrderNotFoundException ex) {
        log.error("Order not found exception occurred: {}", ex.getMessage());
        var errorResponseDto = new ErrorResponseDto(
                "Order not found",
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                LocalDateTime.now());

        return new ResponseEntity<>(errorResponseDto, HttpStatus.NOT_FOUND);

    }
    /// MENU

    @ExceptionHandler(MenuItemNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleMenuItemNotFoundException(MenuItemNotFoundException ex) {
        log.error("MenuItem not found exception occurred: {}", ex.getMessage());
        var errorResponseDto = new ErrorResponseDto(
                "MenuItem not found",
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                LocalDateTime.now());

        return new ResponseEntity<>(errorResponseDto, HttpStatus.NOT_FOUND);

    }
    /// Delivery
    @ExceptionHandler(DeliveryNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleDeliveryNotFoundException(DeliveryNotFoundException ex) {
        log.error("Delivery not found exception occurred: {}", ex.getMessage());
        var errorResponseDto = new ErrorResponseDto(
                "Dellivery not found",
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                LocalDateTime.now());

        return new ResponseEntity<>(errorResponseDto, HttpStatus.NOT_FOUND);

    }

    ///

    @ExceptionHandler(OrderItemNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleOrderItemNotFoundException(OrderItemNotFoundException ex) {
        log.error("OrderItem not found exception occurred: {}", ex.getMessage());
        var errorResponseDto = new ErrorResponseDto(
                "Item not found",
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                LocalDateTime.now());

        return new ResponseEntity<>(errorResponseDto, HttpStatus.NOT_FOUND);

    }


    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleUserNotFoundException(UserNotFoundException ex) {
        log.error("User not found exception occurred: {}", ex.getMessage());
        var errorResponseDto = new ErrorResponseDto(
                "User not found",
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                LocalDateTime.now());

        return new ResponseEntity<>(errorResponseDto, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(DuplicateUserException.class)
    public ResponseEntity<ErrorResponseDto> handlerDuplicateUserException(DuplicateUserException ex) {
        log.error("User already exists: {}", ex.getMessage());
        var errorResponseDto = new ErrorResponseDto(
                "User  duplicate Not  found",
                HttpStatus.CONFLICT,
                ex.getMessage(),
                LocalDateTime.now());

        return new ResponseEntity<>(errorResponseDto, HttpStatus.NOT_FOUND);

    }



}
