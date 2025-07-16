package com.food_delivery.zomato_backend.dtos.OrderDtos;


import com.food_delivery.zomato_backend.dtos.OrderItemDtos.OrderItemRequestDto;
import com.food_delivery.zomato_backend.enumTypes.OrderType;
import com.food_delivery.zomato_backend.enumTypes.PaymentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter  @Setter
public class OrderRequestDto {
    @NotNull(message = "Type is required")
    private OrderType type;
    @NotNull(message = "User ID is required")
    private Long userId;
    private List<OrderItemRequestDto> items;
    private PaymentType paymentType;
    private String deliveryAddress;

}
