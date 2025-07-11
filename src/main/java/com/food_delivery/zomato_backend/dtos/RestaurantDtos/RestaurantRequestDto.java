package com.food_delivery.zomato_backend.dtos.RestaurantDtos;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;


@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantRequestDto {
    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "Address is required")
    private String address;
    @NotBlank(message = "Location is required")
    private String location;
    @NotBlank
    @Size(min = 10, max = 10)
    private String phoneNumber;
    @Email(message = "Email is not valid")
    private String email;
    @NotBlank
    @Builder.Default
    private Integer rating = 4;
    private LocalTime openingTime;
    private LocalTime closingTime;
    private Long ownerId; ///Id of the user who owns the restaurant
}
