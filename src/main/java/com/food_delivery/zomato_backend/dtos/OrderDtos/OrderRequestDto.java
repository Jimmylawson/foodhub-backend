package com.food_delivery.zomato_backend.dtos.OrderDtos;


import com.food_delivery.zomato_backend.dtos.OrderItemDtos.OrderItemRequestDto;
import com.food_delivery.zomato_backend.enumTypes.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDto {
    private Long userId;
    private List<OrderItemRequestDto> items;
    private String deliveryAddress;
    private PaymentType paymentType;
}
