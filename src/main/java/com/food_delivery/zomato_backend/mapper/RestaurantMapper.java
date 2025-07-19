package com.food_delivery.zomato_backend.mapper;


import com.food_delivery.zomato_backend.dtos.RestaurantDtos.RestaurantRequestDto;
import com.food_delivery.zomato_backend.dtos.RestaurantDtos.RestaurantResponseDto;
import com.food_delivery.zomato_backend.entity.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface RestaurantMapper {
    @Mapping(target = "id", source = "id")
    RestaurantResponseDto toRestaurantResponseDto(Restaurant restaurant);
    Restaurant toEntity(RestaurantRequestDto restaurantRequestDto);

    @Mapping(target = "id", ignore = true) /// Never updated id
    @Mapping(target = "menuItems", ignore = true)
    void updateRestaurantFromDto(RestaurantRequestDto restaurantRequestDto, @MappingTarget Restaurant restaurant);
}
