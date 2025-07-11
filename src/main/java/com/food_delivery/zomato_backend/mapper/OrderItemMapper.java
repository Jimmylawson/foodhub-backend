package com.food_delivery.zomato_backend.mapper;


import com.food_delivery.zomato_backend.dtos.OrderDtos.OrderRequestDto;
import com.food_delivery.zomato_backend.dtos.OrderItemDtos.OrderItemResponseDto;
import com.food_delivery.zomato_backend.entity.OrderItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {
    OrderItemResponseDto toOrderItemResponseDto(OrderItem orderItem);
    OrderItem toEntity(OrderRequestDto orderRequesDto);
}
