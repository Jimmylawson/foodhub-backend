package com.food_delivery.zomato_backend.dtos.RestaurantDtos;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class RestaurantRequestDto {
    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "Address is required")
    private String address;
    @NotBlank(message = "Location is required")
    private String location;
    @NotNull
    @DecimalMin("-90.0") @DecimalMax("90.0")
    private Double latitude;

    @NotNull
    @DecimalMin("-180.0") @DecimalMax("180.0")
    private Double longitude;

    @NotBlank
    @Size(min = 10, max = 10)
    private String phoneNumber;
    @Email(message = "Email is not valid")
    private String email;
    @NotNull(message = "Rating is required")
    private BigDecimal rating = BigDecimal.valueOf(4.0);
    @JsonFormat(pattern = "HH:mm")
    private LocalTime openingTime;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime closingTime;
    private Long ownerId; ///Id of the user who owns the restaurant
}
