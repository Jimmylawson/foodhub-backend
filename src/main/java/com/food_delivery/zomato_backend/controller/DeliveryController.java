package com.food_delivery.zomato_backend.controller;


import com.food_delivery.zomato_backend.service.DeliveryService.DeliveryServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/delivery")
@RequiredArgsConstructor
public class DeliveryController {
    private final DeliveryServiceInterface deliveryServiceInterface;

    /// Create endpoints


    /// Read endpoints

    ///Update endpoints

    ///delete endpoints
}
