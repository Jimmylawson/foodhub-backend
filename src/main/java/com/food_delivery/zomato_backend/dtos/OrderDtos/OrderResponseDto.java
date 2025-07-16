package com.food_delivery.zomato_backend.dtos.OrderDtos;


import com.fasterxml.jackson.annotation.*;
import com.food_delivery.zomato_backend.dtos.DeliveryDtos.DeliveryResponseDto;
import com.food_delivery.zomato_backend.dtos.OrderItemDtos.OrderItemResponseDto;
import com.food_delivery.zomato_backend.dtos.PaymentDtos.PaymentResponseDto;
import com.food_delivery.zomato_backend.dtos.UserDtos.UserBasicDto;
import com.food_delivery.zomato_backend.enumTypes.OrderType;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter @Getter
public class OrderResponseDto {
    private Long id;
    private OrderType type;
    private BigDecimal totalPrice;
    private UserBasicDto user;

    private List<OrderItemResponseDto> items;
    
    @JsonIgnoreProperties({"order"})
    private PaymentResponseDto payment;
    
    @JsonIgnoreProperties({"order"})
    private DeliveryResponseDto delivery;
    private LocalDateTime orderDate;
}
