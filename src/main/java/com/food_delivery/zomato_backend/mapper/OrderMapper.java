package com.food_delivery.zomato_backend.mapper;


import com.food_delivery.zomato_backend.dtos.OrderDtos.OrderRequestDto;
import com.food_delivery.zomato_backend.dtos.OrderDtos.OrderResponseDto;
import com.food_delivery.zomato_backend.entity.Order;
import com.food_delivery.zomato_backend.mapper.userMappers.UserBasicMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;
import static java.math.BigDecimal.ZERO;
import static java.math.BigDecimal.valueOf;

@Mapper(
        componentModel = "spring",
        uses = {
                UserBasicMapper.class,
                OrderItemMapper.class,
                PaymentMapper.class,
                DeliveryMapper.class
        }
)
public interface OrderMapper {
    
    @Mapping(target = "totalPrice", expression = "java(order.getOrderItems().stream().map(orderItem -> orderItem.getPrice().multiply(java.math.BigDecimal.valueOf(orderItem.getQuantity()))).reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add))")
    @Mapping(target = "orderDate", source = "createdAt")
    @Mapping(target = "items", source = "orderItems")
    OrderResponseDto toOrderResponseDto(Order order);

    Order toEntity(OrderRequestDto orderRequestDto);
}
