package com.food_delivery.zomato_backend.service.DeliveryService;


import com.food_delivery.zomato_backend.dtos.DeliveryDtos.DeliveryRequestDto;
import com.food_delivery.zomato_backend.dtos.DeliveryDtos.DeliveryResponseDto;
import com.food_delivery.zomato_backend.entity.Delivery;
import com.food_delivery.zomato_backend.enumTypes.DeliveryStatus;
import com.food_delivery.zomato_backend.enumTypes.Mode;
import com.food_delivery.zomato_backend.exceptions.orderException.OrderNotFoundException;
import com.food_delivery.zomato_backend.exceptions.restaurantException.MenuItemNotFoundException;
import com.food_delivery.zomato_backend.exceptions.users.UserNotFoundException;
import com.food_delivery.zomato_backend.mapper.DeliveryMapper;
import com.food_delivery.zomato_backend.repository.DeliveryRepository;
import com.food_delivery.zomato_backend.repository.OrderRepository;
import com.food_delivery.zomato_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliveryServiceImpl  implements DeliveryServiceInterface {
    private final DeliveryMapper deliveryMapper;
    private final DeliveryRepository deliveryRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    /// Find the order
    public Delivery getDeliveryOrThrowError(Long id) {
        return deliveryRepository.findById(id)
                .orElseThrow(() -> new MenuItemNotFoundException(id));
    }

/// Save delivery in database
    @Override
    public DeliveryResponseDto saveDelivery(DeliveryRequestDto deliveryRequestDto) {
        var order = orderRepository.findById(deliveryRequestDto.getOrderId())
                .orElseThrow(() -> new OrderNotFoundException(deliveryRequestDto.getOrderId()));
        var deliveryPerson = userRepository.findById(deliveryRequestDto.getDeliveryPersonId())
                .orElseThrow(() -> new UserNotFoundException(deliveryRequestDto.getDeliveryPersonId()));

        LocalTime pickupTime = LocalTime.now();
        LocalTime deliveryTime = pickupTime.plusMinutes(30);

        Delivery delivery = Delivery.builder()
                .order(order)
                .deliveryPerson(deliveryPerson)
                .mode(deliveryRequestDto.getMode() != null ? deliveryRequestDto.getMode() : Mode.DELIVERY)
                .deliveryStatus(deliveryRequestDto.getDeliveryStatus() != null ? deliveryRequestDto.getDeliveryStatus() : DeliveryStatus.PENDING)
                .pickupTime(pickupTime)
                .deliveryTime(deliveryTime)
                .address(deliveryRequestDto.getAddress())
                .build();

        Delivery savedDelivery = deliveryRepository.save(delivery);


        return deliveryMapper.toDeliveryResponseDto(savedDelivery);
    }


    @Override
    public DeliveryResponseDto getDelivery(Long id) {
        return deliveryMapper.toDeliveryResponseDto(getDeliveryOrThrowError(id));
    }

    @Override
    public DeliveryResponseDto updateDelivery(Long id, DeliveryRequestDto deliveryRequestDto) {
        var delivery = getDeliveryOrThrowError(id);

        if (deliveryRequestDto.getOrderId() != null) {
            var order = orderRepository.findById(deliveryRequestDto.getOrderId()
            ).orElseThrow(() -> new OrderNotFoundException(deliveryRequestDto.getOrderId()));
            delivery.setOrder(order);
        }
        if (deliveryRequestDto.getDeliveryPersonId() != null) {
            var deliveryPerson = userRepository.findById(deliveryRequestDto.getDeliveryPersonId())
                    .orElseThrow(() -> new UserNotFoundException(deliveryRequestDto.getDeliveryPersonId()));
            delivery.setDeliveryPerson(deliveryPerson);
        }
        if (deliveryRequestDto.getMode() != null
                && !deliveryRequestDto.getMode().equals(delivery.getMode())) {
            delivery.setMode(deliveryRequestDto.getMode());
        }
        if (deliveryRequestDto.getDeliveryStatus() != null
                && !deliveryRequestDto.getDeliveryStatus().equals(delivery.getDeliveryStatus())) {
            delivery.setDeliveryStatus(deliveryRequestDto.getDeliveryStatus());
        }
        if (deliveryRequestDto.getAddress() != null
                && !deliveryRequestDto.getAddress().equals(delivery.getAddress())) {
            delivery.setAddress(deliveryRequestDto.getAddress());
        }

        if(deliveryRequestDto.getDeliveryTime() != null){
            delivery.setDeliveryTime(deliveryRequestDto.getDeliveryTime());
        }

        if(delivery.getPickupTime() == null)
            delivery.setPickupTime(LocalTime.now());


        var updatedDelivery = deliveryRepository.save(delivery);

        return deliveryMapper.toDeliveryResponseDto(updatedDelivery);

    }

    @Override
    public void deleteDelivery(Long id) {
        deliveryRepository.delete(getDeliveryOrThrowError(id));
    }

    @Override
    public Page<DeliveryResponseDto> getAllDeliveries(Pageable pageable) {

        return deliveryRepository.findAll(pageable)
                .map(deliveryMapper::toDeliveryResponseDto);
    }
}
