package com.food_delivery.zomato_backend.dtos.OrderDtos;


import com.food_delivery.zomato_backend.dtos.DeliveryDtos.DeliveryResponseDto;
import com.food_delivery.zomato_backend.dtos.OrderItemDtos.OrderItemResponseDto;
import com.food_delivery.zomato_backend.dtos.PaymentDtos.PaymentResponseDto;
import com.food_delivery.zomato_backend.dtos.UserDtos.UserBasicDto;
import com.food_delivery.zomato_backend.enumTypes.OrderType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderResponseDto {
    private Long id;
    private OrderType type;
    private BigDecimal totalPrice;
    private UserBasicDto user;
    private List<OrderItemResponseDto> items;
    private PaymentResponseDto payment;
    private DeliveryResponseDto delivery;
    private LocalDateTime orderDate;
}
