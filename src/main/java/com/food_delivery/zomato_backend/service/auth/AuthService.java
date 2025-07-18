package com.food_delivery.zomato_backend.service.auth;

import com.food_delivery.zomato_backend.dtos.authDto.AuthRequest;
import com.food_delivery.zomato_backend.dtos.authDto.AuthResponse;

public interface AuthService {
    AuthResponse authenticate(AuthRequest request);
    AuthResponse refreshToken(String refreshToken);
//    void forgotPassword(String email);
//    void resetPassword(String token, String newPassword);
}
