package com.food_delivery.zomato_backend.mapper;


import com.food_delivery.zomato_backend.dtos.MenuItemDtos.MenuItemRequestDto;
import com.food_delivery.zomato_backend.dtos.MenuItemDtos.MenuItemResponseDto;
import org.mapstruct.Mapper;

import com.food_delivery.zomato_backend.entity.MenuItem;

@Mapper(componentModel = "spring")
public interface MenuItemMapper {
    MenuItemResponseDto toMenuItemResponseDto(MenuItem menuItem);
    MenuItem toEntity(MenuItemRequestDto menuItemRequestdto);
}
