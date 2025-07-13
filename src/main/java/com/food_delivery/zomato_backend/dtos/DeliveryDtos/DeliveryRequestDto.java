package com.food_delivery.zomato_backend.dtos.DeliveryDtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.food_delivery.zomato_backend.enumTypes.DeliveryStatus;
import com.food_delivery.zomato_backend.enumTypes.Mode;
import com.food_delivery.zomato_backend.enumTypes.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

;import java.time.LocalTime;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DeliveryRequestDto {

    @NotNull(message = "Order ID is required")
    private Long orderId;
    @NotNull(message = "Mode is required")
    private Mode mode; /// If it is a pickup
    @NotNull(message = "Delivery status is required")
    private DeliveryStatus deliveryStatus;
    @NotNull(message = "Delivery person Id is required")
    private Long deliveryPersonId;
    @NotBlank(message = "Delivery address is required")
    private String address;
    @NotNull(message = "Delivery time is required")
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime deliveryTime;


}
