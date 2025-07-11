package com.food_delivery.zomato_backend.mapper;


import com.food_delivery.zomato_backend.dtos.DeliveryDtos.DeliveryRequestDto;
import com.food_delivery.zomato_backend.dtos.DeliveryDtos.DeliveryResponseDto;
import com.food_delivery.zomato_backend.entity.Delivery;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DeliveryMapper {
    DeliveryResponseDto toDeliveryResponseDto(Delivery delivery);
    Delivery toEntity(DeliveryRequestDto deliveryRequestDto);
}
