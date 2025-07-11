package com.food_delivery.zomato_backend.exceptions.restaurantException;



public class RestaurantNotFoundException extends RuntimeException {
    public RestaurantNotFoundException(Long restaurantId ) {
        super("Restaurant with ID " + restaurantId + " not found.");
    }
}
