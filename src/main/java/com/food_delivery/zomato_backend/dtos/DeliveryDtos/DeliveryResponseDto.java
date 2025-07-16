package com.food_delivery.zomato_backend.dtos.DeliveryDtos;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.food_delivery.zomato_backend.enumTypes.DeliveryStatus;
import com.food_delivery.zomato_backend.enumTypes.Mode;
import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryResponseDto {
    private Long id;
    /// Order reference
    private Long orderId;

    /// Delivery person
    private Long deliveryPersonId;
    private String deliveryPersonName;
    /// Delivery mode
    private Mode mode;

    ///Timestamps
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime pickupTime;
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime deliveryTime;

    private DeliveryStatus status;
    private String address;


}
