package com.food_delivery.zomato_backend.controller;

import com.food_delivery.zomato_backend.service.UserService.UserServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserServiceInterface userService;
    /// Create endpoints


    /// Read endpoints

    ///Update endpoints

    ///delete endpoints

}
