package com.food_delivery.zomato_backend.service.MenuItemService;


import com.food_delivery.zomato_backend.dtos.MenuItemDtos.MenuItemRequestDto;
import com.food_delivery.zomato_backend.dtos.MenuItemDtos.MenuItemResponseDto;

import java.util.List;

public interface MenuItemServiceInterface {
    MenuItemResponseDto saveMenuItem(MenuItemRequestDto menuItemRequestDto);
    MenuItemResponseDto updateMenuItem(Long menuItemId, MenuItemRequestDto menuItemRequestDto);
    void deleteMenuItem(Long menuItemId);
    MenuItemResponseDto getMenuItem(Long menuItemId);
    List<MenuItemResponseDto> getMenuItemByRestaurantId(Long id);

}
