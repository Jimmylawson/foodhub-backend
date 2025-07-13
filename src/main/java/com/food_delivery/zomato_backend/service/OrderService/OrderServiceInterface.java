package com.food_delivery.zomato_backend.service.OrderService;


import com.food_delivery.zomato_backend.dtos.OrderDtos.OrderRequestDto;
import com.food_delivery.zomato_backend.dtos.OrderDtos.OrderResponseDto;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderServiceInterface {
    OrderResponseDto saveOrder(OrderRequestDto orderRequestDto);
    OrderResponseDto updateOrder(Long orderId, OrderRequestDto orderRequestDto);
    OrderResponseDto getOrder(Long orderId);
    void deleteOrder(Long orderId);
}
