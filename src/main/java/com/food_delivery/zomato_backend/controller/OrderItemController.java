package com.food_delivery.zomato_backend.controller;

import com.food_delivery.zomato_backend.service.OrderItemService.OrderItemServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/order-items")
@RequiredArgsConstructor
public class OrderItemController {
    private final OrderItemServiceInterface orderItemService;
    /// Create endpoints


    /// Read endpoints

    ///Update endpoints

    ///delete endpoints
}
