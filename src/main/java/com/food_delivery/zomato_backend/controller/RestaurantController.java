package com.food_delivery.zomato_backend.controller;

import com.food_delivery.zomato_backend.service.RestaurantService.RestaurantServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/restaurants")
@RequiredArgsConstructor
public class RestaurantController {
    private final RestaurantServiceInterface restaurantService;

    /// Create endpoints


    /// Read endpoints

    ///Update endpoints

    ///delete endpoints
}
