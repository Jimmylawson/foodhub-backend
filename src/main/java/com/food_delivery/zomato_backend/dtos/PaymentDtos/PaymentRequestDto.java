package com.food_delivery.zomato_backend.dtos.PaymentDtos;


import com.food_delivery.zomato_backend.dtos.OrderDtos.OrderRequestDto;
import com.food_delivery.zomato_backend.enumTypes.PaymentType;
import com.food_delivery.zomato_backend.enumTypes.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentRequestDto {
    @NotBlank(message = "Order details are required")
    private OrderRequestDto order;
    @NotNull(message = "Payment type is required")
    private PaymentType paymentType;
    @NotNull(message = "Status is required")
    private Status status = Status.PENDING;
    @NotBlank(message = "Transaction id is required")
    private String transactionId;

    private LocalDateTime paidAt;

}
