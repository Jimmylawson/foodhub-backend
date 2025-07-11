package com.food_delivery.zomato_backend.exceptions;

public class DuplicateRestaurantException extends RuntimeException {
    public DuplicateRestaurantException(String s) {
        super(s);
    }
}
