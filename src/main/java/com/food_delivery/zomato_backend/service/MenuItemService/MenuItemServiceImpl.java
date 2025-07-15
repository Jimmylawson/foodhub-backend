package com.food_delivery.zomato_backend.service.MenuItemService;

import com.food_delivery.zomato_backend.dtos.MenuItemDtos.MenuItemRequestDto;
import com.food_delivery.zomato_backend.dtos.MenuItemDtos.MenuItemResponseDto;
import com.food_delivery.zomato_backend.entity.MenuItem;

import com.food_delivery.zomato_backend.exceptions.restaurantException.MenuItemNotFoundException;
import com.food_delivery.zomato_backend.exceptions.restaurantException.RestaurantNotFoundException;
import com.food_delivery.zomato_backend.mapper.MenuItemMapper;
import com.food_delivery.zomato_backend.repository.MenuItemRepository;
import com.food_delivery.zomato_backend.repository.OrderRepository;
import com.food_delivery.zomato_backend.repository.RestaurantRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class MenuItemServiceImpl implements MenuItemServiceInterface {
    private final MenuItemMapper menuItemMapper;
    private final MenuItemRepository menuItemRepository;
    private final RestaurantRepository restaurantRepository;
    private final OrderRepository orderRepository;


    /// Find the order
    public MenuItem getMenuItemOrThrowError(Long id) {
        return menuItemRepository.findById(id)
                .orElseThrow(() -> new MenuItemNotFoundException(id));
    }


    @Override
    public MenuItemResponseDto saveMenuItem(MenuItemRequestDto menuItemRequestDto) {
        ///retrieve the restaurant by id
        var restaurant = restaurantRepository.findById(menuItemRequestDto.getRestaurantId())
                .orElseThrow(() -> new RestaurantNotFoundException(menuItemRequestDto.getRestaurantId()));
        /// Save the menuItem
        var menuItem  = MenuItem.builder()
                .name(menuItemRequestDto.getName())
                .description(menuItemRequestDto.getDescription())
                .price(menuItemRequestDto.getPrice())
                .type(menuItemRequestDto.getType())
                .restaurant(restaurant)
                .build();

        return menuItemMapper.toMenuItemResponseDto(menuItemRepository.save(menuItem));
    }

    @Override
    @Transactional
    public MenuItemResponseDto updateMenuItem(Long menuItemId, MenuItemRequestDto menuItemRequestDto) {
        var menuItem = getMenuItemOrThrowError(menuItemId);

        if(menuItemRequestDto.getName() != null)
            menuItem.setName(menuItemRequestDto.getName());
        if(menuItemRequestDto.getDescription() != null)
            menuItem.setDescription(menuItemRequestDto.getDescription());
        if(menuItemRequestDto.getPrice() != null)
            menuItem.setPrice(menuItemRequestDto.getPrice());
        if(menuItemRequestDto.getType() != null)
            menuItem.setType(menuItemRequestDto.getType());

        return menuItemMapper.toMenuItemResponseDto(menuItemRepository.save(menuItem));
    }

    @Override
    @Transactional
    public void deleteMenuItem(Long menuItemId) {
        var menuItem = getMenuItemOrThrowError(menuItemId);
        menuItemRepository.delete(menuItem);
    }

    @Override
    public MenuItemResponseDto getMenuItem(Long menuItemId) {
        var menuItem = getMenuItemOrThrowError(menuItemId);

        return menuItemMapper.toMenuItemResponseDto(menuItem);
    }

    @Override
    public Page<MenuItemResponseDto> getAllMenuItemByRestaurantId(Long restaurantId, Pageable pageable) {
      if(!restaurantRepository.existsById(restaurantId))
          throw new RestaurantNotFoundException(restaurantId);
        return menuItemRepository
                .findByRestaurantId(restaurantId,pageable)
                .map(menuItemMapper::toMenuItemResponseDto);
    }
}
