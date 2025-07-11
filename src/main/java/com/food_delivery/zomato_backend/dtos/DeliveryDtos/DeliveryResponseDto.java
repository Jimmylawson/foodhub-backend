package com.food_delivery.zomato_backend.dtos.DeliveryDtos;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.food_delivery.zomato_backend.enumTypes.Mode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryResponseDto {
    private Long id;


    /// Order reference
    private Long orderId;


    /// Delivery mode
    private Mode mode;

    ///Timestamps

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime pickupTime;
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime deliveryTime;

    @Builder.Default
    private String status = "PENDING";

    /// Calculated field
    public String getEstimatedDeliveryTime() {
        return pickupTime != null ? pickupTime.plusMinutes(30).toString() : "N/A";
    }
}
