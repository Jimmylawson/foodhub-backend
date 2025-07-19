package com.food_delivery.zomato_backend.Service;

import com.food_delivery.zomato_backend.dtos.OrderDtos.OrderRequestDto;
import com.food_delivery.zomato_backend.dtos.OrderDtos.OrderResponseDto;
import com.food_delivery.zomato_backend.dtos.OrderItemDtos.OrderItemRequestDto;
import com.food_delivery.zomato_backend.dtos.OrderItemDtos.OrderItemResponseDto;
import com.food_delivery.zomato_backend.dtos.UserDtos.UserBasicDto;
import com.food_delivery.zomato_backend.entity.MenuItem;
import com.food_delivery.zomato_backend.entity.Order;
import com.food_delivery.zomato_backend.entity.User;
import com.food_delivery.zomato_backend.enumTypes.OrderType;
import com.food_delivery.zomato_backend.enumTypes.PaymentType;
import com.food_delivery.zomato_backend.enumTypes.Role;
import com.food_delivery.zomato_backend.mapper.OrderMapper;
import com.food_delivery.zomato_backend.repository.MenuItemRepository;
import com.food_delivery.zomato_backend.repository.OrderRepository;
import com.food_delivery.zomato_backend.repository.UserRepository;
import com.food_delivery.zomato_backend.service.OrderService.OrderServiceImpl;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.quality.Strictness;
import org.mockito.junit.jupiter.MockitoSettings;

import com.food_delivery.zomato_backend.enumTypes.Mode;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class OrderServiceTest {
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private OrderMapper orderMapper;
    @Mock
    private MenuItemRepository menuItemRepository;
    @Mock
    private UserRepository userRepository;
    // orderRepository is already defined above
    @InjectMocks
    private OrderServiceImpl orderService;
    private User user;


    private OrderRequestDto orderRequestDto;
    private OrderResponseDto orderResponseDto;
    private Order order;


    @BeforeEach
    public void setup() {
        // Setup test user
        user = User.builder()
                .id(1L)
                .username("Test User")
                .email("hZ1lS@example.com")
                .phoneNumber("1234567890")
                .address("123 Test St")
                .role(Role.USER)
                .build();

        // Setup test order request DTO
        orderRequestDto = OrderRequestDto.builder()
                .type(OrderType.DELIVERY)
                .userId(user.getId())
                .paymentType(PaymentType.DEBIT_CARD)
                .deliveryAddress("123 Test St")
                .items(List.of(
                        new OrderItemRequestDto(1L, 2, 3L),
                        new OrderItemRequestDto(2L, 2, 3L)
                ))
                .build();

        // Setup test order
        order = Order.builder()
                .id(1L)
                .type(OrderType.DELIVERY)
                .user(user)
                .price(new BigDecimal("100"))
                .orderItems(new ArrayList<>())
                .delivery(null)
                .payment(null)
                .build();

        orderResponseDto = OrderResponseDto.builder()
                .id(1L)
                .type(OrderType.DELIVERY)
                .user(UserBasicDto.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .email(user.getEmail())
                        .build())
                .items(List.of(
                        OrderItemResponseDto.builder()
                                .id(1L)
                                .quantity(2)
                                .price(BigDecimal.valueOf(10L))
                                .menuItem(null)
                                .order(null)
                                .build(),
                        OrderItemResponseDto.builder()
                                .id(2L)
                                .quantity(2)
                                .price(BigDecimal.valueOf(10L))
                                .menuItem(null)
                                .order(null)
                                .build()
                ))
                .build();



    }

    @Test
    @Transactional
    public void saveOrderTest(){
        // Arrange
        // Create a mock menu item that will be returned by the repository
        MenuItem mockMenuItem = new MenuItem();
        mockMenuItem.setId(3L);
        mockMenuItem.setName("Test Item");
        mockMenuItem.setPrice(BigDecimal.TEN);
        
        // Set up the mocks
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(menuItemRepository.findById(anyLong())).thenReturn(Optional.of(mockMenuItem));
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> {
            Order savedOrder = invocation.getArgument(0);
            savedOrder.setId(1L); // Simulate ID generation by DB
            return savedOrder;
        });
        when(orderMapper.toOrderResponseDto(any(Order.class))).thenReturn(orderResponseDto);

        // Act
        OrderResponseDto response = orderService.saveOrder(orderRequestDto);

        // Assert
        assertNotNull(response);
        assertEquals(orderResponseDto, response);
        
        // Verify interactions
        verify(userRepository).findById(anyLong());
        verify(menuItemRepository, atLeastOnce()).findById(anyLong());
        verify(orderRepository).save(any(Order.class));
        verify(orderMapper).toOrderResponseDto(any(Order.class));


    }


}
