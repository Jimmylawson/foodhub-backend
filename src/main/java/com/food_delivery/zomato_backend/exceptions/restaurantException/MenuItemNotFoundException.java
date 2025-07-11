package com.food_delivery.zomato_backend.exceptions.restaurantException;

public class MenuItemNotFoundException extends RuntimeException {
    public MenuItemNotFoundException(Long id) {
        super("MenuItem not found with id: " + id);
    }
}
