package com.food_delivery.zomato_backend.dtos.RestaurantDtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.food_delivery.zomato_backend.dtos.MenuItemDtos.MenuItemResponseDto;
import com.food_delivery.zomato_backend.dtos.UserDtos.UserBasicDto;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
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
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_FLOAT)
    private Double latitude;
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_FLOAT)
    private Double longitude;
    private String location;
    private String phoneNumber;
    private String email;
    private BigDecimal rating;
    private LocalTime openingTime;
    private LocalTime closingTime;
    private UserBasicDto owner;
    private List<MenuItemResponseDto> menuItems;

    /// Optional: Add these if you track them

}
