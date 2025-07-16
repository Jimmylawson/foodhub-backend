package com.food_delivery.zomato_backend.service.OrderItemService;

import com.food_delivery.zomato_backend.dtos.OrderDtos.OrderRequestDto;
import com.food_delivery.zomato_backend.dtos.OrderItemDtos.OrderItemRequestDto;
import com.food_delivery.zomato_backend.dtos.OrderItemDtos.OrderItemResponseDto;
import com.food_delivery.zomato_backend.entity.Order;
import com.food_delivery.zomato_backend.entity.OrderItem;
import com.food_delivery.zomato_backend.exceptions.orderException.OrderNotFoundException;
import com.food_delivery.zomato_backend.exceptions.restaurantException.MenuItemNotFoundException;
import com.food_delivery.zomato_backend.exceptions.restaurantException.RestaurantNotFoundException;
import com.food_delivery.zomato_backend.mapper.OrderItemMapper;
import com.food_delivery.zomato_backend.repository.MenuItemRepository;
import com.food_delivery.zomato_backend.repository.OrderItemRepository;
import com.food_delivery.zomato_backend.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor

public class OrderItemServiceImpl implements OrderItemServiceInterface {
    private final OrderItemRepository orderItemRepository;
    private final OrderItemMapper orderItemMapper;
    private final MenuItemRepository menuItemRepository;
    private final OrderRepository orderRepository;
    /// Find the order
    public OrderItem getOrderItemOrThrowError(Long id) {
        return orderItemRepository.findById(id)
                .orElseThrow(() -> new RestaurantNotFoundException(id));
    }

    @Override
    public OrderItemResponseDto saveOrderItem(OrderItemRequestDto orderRequestDto) {

        var menuItem = menuItemRepository.findById(orderRequestDto.getMenuItemId())
                .orElseThrow(() -> new MenuItemNotFoundException(orderRequestDto.getMenuItemId()));
        var order = orderRepository.findById(orderRequestDto.getOrderId())
                .orElseThrow(() -> new OrderNotFoundException(orderRequestDto.getOrderId()));

        var orderItem = OrderItem.builder()
                .menuItem(menuItem)
                .quantity(orderRequestDto.getQuantity())
                .price(menuItem.getPrice()) // single item price
                .order(order)
                .build();

        return orderItemMapper.toOrderItemResponseDto(orderItemRepository.save(orderItem));
    }

    @Override
    @Transactional
    public OrderItemResponseDto updateOrderItem(Long id, OrderItemRequestDto orderItemRequestDto) {
       var orderItem = getOrderItemOrThrowError(id);

      if(orderItemRequestDto.getOrderId() != null) {
          var order = orderRepository.findById(orderItemRequestDto.getOrderId())
                          .orElseThrow(() -> new OrderNotFoundException(orderItemRequestDto.getOrderId()));
          orderItem.setOrder(order);
      }
      if(orderItemRequestDto.getMenuItemId() != null) {
          var menuItem = menuItemRepository.findById(orderItemRequestDto.getMenuItemId())
                          .orElseThrow(() -> new MenuItemNotFoundException(orderItemRequestDto.getMenuItemId()));
          orderItem.setMenuItem(menuItem);
      }
      if(orderItemRequestDto.getQuantity() != null
      && !orderItemRequestDto.getQuantity().equals(orderItem.getQuantity())) {
          orderItem.setQuantity(orderItemRequestDto.getQuantity());
      }


        return orderItemMapper.toOrderItemResponseDto(orderItemRepository.save(orderItem));
    }


    @Override
    public OrderItemResponseDto getOrderItem(Long id) {
        return orderItemMapper.toOrderItemResponseDto(getOrderItemOrThrowError(id));
    }


    @Override
    @Transactional
    public void deleteOrderItem(Long id) {
        orderItemRepository.delete(getOrderItemOrThrowError(id));

    }
}
