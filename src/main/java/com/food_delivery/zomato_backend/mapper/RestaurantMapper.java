package com.food_delivery.zomato_backend.mapper;


import com.food_delivery.zomato_backend.dtos.RestaurantDtos.RestaurantResponseDto;
import com.food_delivery.zomato_backend.dtos.UserDtos.UserRequestDto;
import com.food_delivery.zomato_backend.entity.Restaurant;
import com.food_delivery.zomato_backend.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RestaurantMapper {

    RestaurantResponseDto toRestaurantResponseDto(Restaurant restaurant);
    User toEntity(UserRequestDto userRequestDto);
}
