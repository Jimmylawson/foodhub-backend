package com.food_delivery.zomato_backend.enumTypes;

// In enumTypes/DeliveryStatus.java
public enum DeliveryStatus {
    PENDING,           // When delivery is created
    CONFIRMED,         // Restaurant has confirmed
    PREPARING,         // Food is being prepared
    READY_FOR_PICKUP,  // Ready for delivery person to pick up
    OUT_FOR_DELIVERY,  // On the way to customer
    DELIVERED,         // Successfully delivered
    CANCELLED,         // Order was cancelled
    FAILED             // Delivery failed (couldn't complete)
}