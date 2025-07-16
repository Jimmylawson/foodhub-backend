package com.food_delivery.zomato_backend.mapper;


import com.food_delivery.zomato_backend.dtos.DeliveryDtos.DeliveryRequestDto;
import com.food_delivery.zomato_backend.dtos.DeliveryDtos.DeliveryResponseDto;
import com.food_delivery.zomato_backend.entity.Delivery;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DeliveryMapper {
    @Mapping(target ="deliveryPersonId", source = "delivery.deliveryPerson.id")
    @Mapping(target = "deliveryPersonName", source = "delivery.deliveryPerson.username")
    DeliveryResponseDto toDeliveryResponseDto(Delivery delivery);
    Delivery toEntity(DeliveryRequestDto deliveryRequestDto);
}
