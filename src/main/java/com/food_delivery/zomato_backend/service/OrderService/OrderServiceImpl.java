package com.food_delivery.zomato_backend.service.OrderService;

import com.food_delivery.zomato_backend.dtos.MenuItemDtos.MenuItemResponseDto;
import com.food_delivery.zomato_backend.dtos.OrderDtos.OrderRequestDto;
import com.food_delivery.zomato_backend.dtos.OrderDtos.OrderResponseDto;
import com.food_delivery.zomato_backend.dtos.OrderItemDtos.OrderItemResponseDto;
import com.food_delivery.zomato_backend.dtos.UserDtos.UserBasicDto;
import com.food_delivery.zomato_backend.entity.Delivery;
import com.food_delivery.zomato_backend.entity.Order;
import com.food_delivery.zomato_backend.entity.OrderItem;
i
import com.food_delivery.zomato_backend.entity.Payment;
import com.food_delivery.zomato_backend.enumTypes.DeliveryStatus;
import com.food_delivery.zomato_backend.enumTypes.OrderType;
import com.food_delivery.zomato_backend.enumTypes.Status;
import com.food_delivery.zomato_backend.exceptions.restaurantException.MenuItemNotFoundException;
import com.food_delivery.zomato_backend.exceptions.restaurantException.RestaurantNotFoundException;
import com.food_delivery.zomato_backend.mapper.OrderMapper;
import com.food_delivery.zomato_backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderServiceInterface {
    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final OrderItemRepository orderItemRepository;
    private final PaymentRepository paymentRepository;
    private final DeliveryRepository deliveryRepository;
    private final MenuItemRepository menuItemRepository;


    /// Find the order
    private Order getRestaurantOrThrowError(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RestaurantNotFoundException(id));
    }


    @Override
    public OrderResponseDto saveOrder(OrderRequestDto orderRequestDto) {
        ///  Fetch the User
        var user = userRepository.findById(orderRequestDto.getUserId())
                .orElseThrow(() -> new RestaurantNotFoundException(orderRequestDto.getUserId()));
        UserBasicDto userBasic = UserBasicDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getUsername())
                .build();

        /// convert and save the orderItem
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

        Order order = Order.builder()
                .type(orderRequestDto.getType())
                .price(totalPrice)
                .user(user)
                .orderItems(orderItems)
                .delivery(null)
                .payment(null)
                .build();



                ///Set reverser relationship: each OrderItem needs its Order
                orderItems.forEach(item -> item.setOrder(order));

        /// Delivery
        Delivery delivery = null;
        if(orderRequestDto.getType() == OrderType.DELIVERY){
            delivery = Delivery.builder()
                    .address(orderRequestDto.getDeliveryAddress())
                    .order(order)
                    .deliveryStatus(DeliveryStatus.PENDING)
                    .build();
        }

        /// Payment
        Payment payment = Payment.builder()
                .order(order)
                .paymentType(orderRequestDto.getPaymentType())
                .status(Status.SUCCESS)
                .transactionId(UUID.randomUUID().toString())
                .paidAt(LocalDateTime.now())
                .build();
        /// Assign the delivery and payment to order
        order.setDelivery(delivery);
        order.setPayment(payment);


                /// 3.Create and save the order entity
        orderRepository.save(order);

        /// Convert and return the response
        return orderMapper.toOrderResponseDto(order);
    }

    @Override
    public OrderResponseDto updateOrder(Long orderId, OrderRequestDto orderRequestDto) {
        return null;
    }

    @Override
    public OrderResponseDto getOrder(Long orderId) {
        return null;
    }

    @Override
    public void deleteOrder(Long orderId) {

    }
}
