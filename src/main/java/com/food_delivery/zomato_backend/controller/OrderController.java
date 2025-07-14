package com.food_delivery.zomato_backend.controller;


import com.food_delivery.zomato_backend.service.OrderService.OrderServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderServiceInterface orderServiceInterface;

    /// Create endpoints


    /// Read endpoints

    ///Update endpoints

    ///delete endpoints
}
