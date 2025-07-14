package com.food_delivery.zomato_backend.controller;

import com.food_delivery.zomato_backend.service.MenuItemService.MenuItemServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/menu-items")
@RequiredArgsConstructor
public class MenuItemController {
    private final MenuItemServiceInterface menuItemServiceInterface;

    /// Create endpoints


    /// Read endpoints

    ///Update endpoints

    ///delete endpoints
}
