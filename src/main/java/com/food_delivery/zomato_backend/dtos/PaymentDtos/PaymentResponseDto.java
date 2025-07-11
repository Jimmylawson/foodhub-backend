package com.food_delivery.zomato_backend.dtos.PaymentDtos;

import com.food_delivery.zomato_backend.dtos.OrderDtos.OrderResponseDto;
import com.food_delivery.zomato_backend.enumTypes.PaymentType;
import com.food_delivery.zomato_backend.enumTypes.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentResponseDto {
    private Long id;
    private OrderResponseDto order;
    private PaymentType paymentType;
    private Status status;
    private String transactionId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "UTC")
    private LocalDateTime paidAt;

}
