package com.food_delivery.zomato_backend.dtos.UserDtos;


import com.food_delivery.zomato_backend.dtos.RestaurantDtos.RestaurantRequestDto;
import com.food_delivery.zomato_backend.entity.Delivery;
import com.food_delivery.zomato_backend.entity.Order;
import com.food_delivery.zomato_backend.entity.Restaurant;
import com.food_delivery.zomato_backend.enumTypes.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter @Getter
public class UserResponseDto {
    private Long id;

    private String username;
    private String email;
    private String address;
    @Pattern(regexp = "^\\+?[0-9\\s-]{10,}$")
    private String phoneNumber;
    private Role role;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
