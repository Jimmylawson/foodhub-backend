package com.food_delivery.zomato_backend.exceptions.users;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(Long userId) {
        super("User with id " + userId + " not found");
    }
}
