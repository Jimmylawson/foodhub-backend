package com.food_delivery.zomato_backend.service.DeliveryService;

import com.food_delivery.zomato_backend.dtos.DeliveryDtos.DeliveryRequestDto;
import com.food_delivery.zomato_backend.dtos.DeliveryDtos.DeliveryResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DeliveryServiceInterface {
    DeliveryResponseDto saveDelivery(DeliveryRequestDto deliveryRequestDto);
    DeliveryResponseDto getDelivery(Long id);
    DeliveryResponseDto updateDelivery(Long id, DeliveryRequestDto deliveryRequestDto);
    void deleteDelivery(Long id);
    Page<DeliveryResponseDto> getAllDeliveries(Pageable pageable);

}
