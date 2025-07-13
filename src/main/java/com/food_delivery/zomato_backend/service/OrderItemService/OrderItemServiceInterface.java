package com.food_delivery.zomato_backend.service.OrderItemService;


import com.food_delivery.zomato_backend.dtos.OrderDtos.OrderRequestDto;
import com.food_delivery.zomato_backend.dtos.OrderItemDtos.OrderItemRequestDto;
import com.food_delivery.zomato_backend.dtos.OrderItemDtos.OrderItemResponseDto;

public interface OrderItemServiceInterface {
    OrderItemResponseDto saveOrderItem(OrderItemRequestDto orderItemRequestDto);
    OrderItemResponseDto updateOrderItem(Long id, OrderItemRequestDto orderItemRequestDto);
    OrderItemResponseDto getOrderItem(Long id);
    void deleteOrderItem(Long id);



}
