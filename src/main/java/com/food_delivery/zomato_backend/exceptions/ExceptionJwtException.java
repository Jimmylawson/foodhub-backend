package com.food_delivery.zomato_backend.exceptions;

public class ExceptionJwtException extends RuntimeException {
    public ExceptionJwtException(String message) {
        super(message);
    }
}
