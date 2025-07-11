package com.food_delivery.zomato_backend.dtos.DeliveryDtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.food_delivery.zomato_backend.enumTypes.Mode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

;


@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryRequestDto {

    @NotNull(message = "Order ID is required")
    private Long orderId;
    @NotNull(message = "Mode is required")
    private Mode mode; /// If it is a pickup

}
