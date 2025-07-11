package com.food_delivery.zomato_backend.dtos.RestaurantDtos;

import com.food_delivery.zomato_backend.dtos.MenuItemDtos.MenuItemResponseDto;
import com.food_delivery.zomato_backend.dtos.UserDtos.UserBasicDto;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RestaurantResponseDto {
    private Long id;
    private String name;
    private String address;
    private String location;
    private String phoneNumber;
    private String email;
    private Integer rating;
    private LocalDateTime openingTime;
    private LocalDateTime closingTime;
    private UserBasicDto owner;
    private List<MenuItemResponseDto> menuItems;

    /// Optional: Add these if you track them

    @NotBlank
    private LocalTime createdAt;
    private LocalTime updatedAt;
}
