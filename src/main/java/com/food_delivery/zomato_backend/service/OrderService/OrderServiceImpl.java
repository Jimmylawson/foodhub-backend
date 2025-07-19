package com.food_delivery.zomato_backend.service.OrderService;

import com.food_delivery.zomato_backend.dtos.OrderDtos.OrderRequestDto;
import com.food_delivery.zomato_backend.dtos.OrderDtos.OrderResponseDto;
import com.food_delivery.zomato_backend.dtos.UserDtos.UserBasicDto;
import com.food_delivery.zomato_backend.entity.Delivery;
import com.food_delivery.zomato_backend.entity.Order;
import com.food_delivery.zomato_backend.entity.OrderItem;

import com.food_delivery.zomato_backend.entity.Payment;
import com.food_delivery.zomato_backend.enumTypes.DeliveryStatus;
import com.food_delivery.zomato_backend.enumTypes.Mode;
import com.food_delivery.zomato_backend.enumTypes.OrderType;
import com.food_delivery.zomato_backend.enumTypes.Status;

import com.food_delivery.zomato_backend.exceptions.restaurantException.MenuItemNotFoundException;
import com.food_delivery.zomato_backend.exceptions.restaurantException.RestaurantNotFoundException;
import com.food_delivery.zomato_backend.exceptions.users.UserNotFoundException;
import com.food_delivery.zomato_backend.mapper.OrderMapper;
import com.food_delivery.zomato_backend.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;



record OrderItemResult(List<OrderItem> orderItems, BigDecimal totalPrice){}

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderServiceInterface {
    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final MenuItemRepository menuItemRepository;


    /// Find the order
    public Order getOrderOrThrowError(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RestaurantNotFoundException(id));
    }


    @Override
    @Transactional
    public OrderResponseDto saveOrder(OrderRequestDto orderRequestDto) {
        log.info("Starting order creation with request: {}", orderRequestDto);
        ///  Fetch the User
        var user = userRepository.findById(orderRequestDto.getUserId())
                .orElseThrow(() -> new UserNotFoundException(orderRequestDto.getUserId()));
        log.info("Fetched user: {}", user);

        /// convert and save the orderItem
        var result = convertToOrderItemsAndCalculateTotal(orderRequestDto);
        List<OrderItem> orderItems = result.orderItems();
        BigDecimal totalPrice = result.totalPrice();

        /// Create the Order
        Order order = Order.builder()
                .type(orderRequestDto.getType())
                .price(totalPrice)
                .user(user)
                .orderItems(orderItems)
                .build();
    log.info("Created order: {}", order);


                ///Set reverser relationship: each OrderItem needs its Order
                orderItems.forEach(item -> item.setOrder(order));
                log.info("Set reverser relationship: each OrderItem needs its Order");

        /// Delivery
        Delivery delivery = null;
        if(orderRequestDto.getType() == OrderType.DELIVERY){
            delivery = Delivery.builder()
                    .address(orderRequestDto.getDeliveryAddress())
                    .order(order)
                    .deliveryStatus(DeliveryStatus.PENDING)
                    .build();
            log.info("Created delivery: {}", delivery);
        }

        /// Payment
        Payment payment = Payment.builder()
                .order(order)
                .paymentType(orderRequestDto.getPaymentType())
                .status(Status.SUCCESS)
                .transactionId(UUID.randomUUID().toString())
                .paidAt(LocalDateTime.now())
                .build();
        log.info("Created payment: {}", payment);
        /// Assign the delivery and payment to order
        order.setDelivery(delivery);
        order.setPayment(payment);
        log.info("Assign the delivery and payment to order");


                /// 3.Create and save the order entity
        orderRepository.save(order);
       log.info("Order saved: {}", order);

        /// Convert and return the response
        return orderMapper.toOrderResponseDto(order);

    }

    @Override
    public OrderResponseDto updateOrder(Long orderId, OrderRequestDto orderRequestDto) {
        /// Fetch the order from the database
        var order  = getOrderOrThrowError(orderId);

        /// Update fields only if it is not null
        if(orderRequestDto.getType() !=null){
            order.setType(orderRequestDto.getType());
        }
        if(orderRequestDto.getPaymentType() !=null && order.getPayment() != null){
            order.getPayment().setPaymentType(orderRequestDto.getPaymentType());

        }
        if(orderRequestDto.getDeliveryAddress() !=null && order.getDelivery() != null && order.getType() == OrderType.DELIVERY){
            order.getDelivery().setAddress(orderRequestDto.getDeliveryAddress());
        }
        /// If item are included update them
        if(orderRequestDto.getItems() != null && !orderRequestDto.getItems().isEmpty()){
            /// Clear the existing items
        order.getOrderItems().clear();
        /// Convert and save the new items
        var result = convertToOrderItemsAndCalculateTotal(orderRequestDto);
        List<OrderItem> updateItems = result.orderItems();
        var totalPrice = result.totalPrice();

        /// Set reverser relationship: each OrderItem needs its Order
        updateItems.forEach(item->item.setOrder(order));
        order.getOrderItems().addAll(updateItems);
        }

        orderRepository.save(order);

        /// Convert and return the response
        return orderMapper.toOrderResponseDto(order);

    }

    private OrderItemResult convertToOrderItemsAndCalculateTotal(OrderRequestDto orderRequestDto) {
        List<OrderItem> orderItems = orderRequestDto.getItems().stream()
                .map(itemDto -> {
                    var menuItem = menuItemRepository.findById(itemDto.getMenuItemId())
                            .orElseThrow(() -> new MenuItemNotFoundException(itemDto.getMenuItemId()));
                    return OrderItem.builder()
                            .menuItem(menuItem)
                            .quantity(itemDto.getQuantity())
                            .price(menuItem.getPrice()) // single item price
                            .build();
                }).toList();



        /// Calculate the total price
        BigDecimal totalPrice = orderItems.stream()
                .map(item->item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new OrderItemResult(orderItems, totalPrice);
    }
    @Override
    public OrderResponseDto getOrder(Long orderId) {
       return orderMapper.toOrderResponseDto(getOrderOrThrowError(orderId));
    }

    @Override
    public Page<OrderResponseDto> getAllOrders(Pageable pageable) {
        return orderRepository.findAll(pageable)
                .map(orderMapper::toOrderResponseDto);
    }

    @Override
    public void deleteOrder(Long orderId) {
        var order = getOrderOrThrowError(orderId);
        orderRepository.delete(order);
    }
}
