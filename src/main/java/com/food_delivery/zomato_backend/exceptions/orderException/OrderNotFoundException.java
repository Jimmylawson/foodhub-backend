package com.food_delivery.zomato_backend.exceptions.orderException;

public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException(Long orderId) {
        super("Order with id " + orderId + " not found");
    }
}
