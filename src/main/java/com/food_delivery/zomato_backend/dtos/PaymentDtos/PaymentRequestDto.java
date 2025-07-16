package com.food_delivery.zomato_backend.dtos.PaymentDtos;


import com.food_delivery.zomato_backend.dtos.OrderDtos.OrderRequestDto;
import com.food_delivery.zomato_backend.enumTypes.PaymentType;
import com.food_delivery.zomato_backend.enumTypes.Status;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentRequestDto {
    @NotNull(message = "Order details are required")
    private Long orderId;
    @NotNull(message = "Payment type is required")
    private PaymentType paymentType;
    @NotBlank(message = "Transaction id is required")
    @Size(max = 100, message = "Transaction id must be less than 100 characters")
    private String transactionId;


}
