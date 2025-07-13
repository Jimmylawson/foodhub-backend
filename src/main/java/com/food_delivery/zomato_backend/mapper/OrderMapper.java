package com.food_delivery.zomato_backend.mapper;


import com.food_delivery.zomato_backend.dtos.OrderDtos.OrderRequestDto;
import com.food_delivery.zomato_backend.dtos.OrderDtos.OrderResponseDto;
import com.food_delivery.zomato_backend.entity.Order;
import com.food_delivery.zomato_backend.mapper.userMappers.UserBasicMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses ={UserBasicMapper.class})
public interface OrderMapper {
    OrderResponseDto toOrderResponseDto(Order order);
    Order toEntity(OrderRequestDto orderRequestDto);
}
