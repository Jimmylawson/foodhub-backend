package com.food_delivery.zomato_backend.dtos.PaymentDtos;

import com.fasterxml.jackson.annotation.*;
import com.food_delivery.zomato_backend.enumTypes.PaymentType;
import com.food_delivery.zomato_backend.enumTypes.Status;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentResponseDto {
    private Long id;
    @JsonIgnore
    private Object order;
    private PaymentType paymentType;
    private Status status;
    private String transactionId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "UTC")
    @FutureOrPresent(message = "Payment data cannot be in the past")
    private LocalDateTime paidAt;

}
