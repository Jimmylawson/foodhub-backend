package com.food_delivery.zomato_backend.mapper;


import com.food_delivery.zomato_backend.dtos.MenuItemDtos.MenuItemRequestDto;
import com.food_delivery.zomato_backend.dtos.MenuItemDtos.MenuItemResponseDto;
import org.mapstruct.Mapper;

import com.food_delivery.zomato_backend.entity.MenuItem;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MenuItemMapper {
    @Mapping(target = "restaurantId", source = "restaurant.id")
    @Mapping(target = "restaurantName", source = "restaurant.name")
    MenuItemResponseDto toMenuItemResponseDto(MenuItem menuItem);
    MenuItem toEntity(MenuItemRequestDto menuItemRequestdto);
}
