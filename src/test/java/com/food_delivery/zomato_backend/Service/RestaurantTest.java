package com.food_delivery.zomato_backend.Service;


import com.food_delivery.zomato_backend.dtos.RestaurantDtos.RestaurantRequestDto;
import com.food_delivery.zomato_backend.dtos.RestaurantDtos.RestaurantResponseDto;
import com.food_delivery.zomato_backend.dtos.UserDtos.UserBasicDto;
import com.food_delivery.zomato_backend.entity.Restaurant;
import com.food_delivery.zomato_backend.entity.User;
import com.food_delivery.zomato_backend.enumTypes.Role;
import com.food_delivery.zomato_backend.exceptions.restaurantException.RestaurantNotFoundException;
import com.food_delivery.zomato_backend.mapper.RestaurantMapper;
import com.food_delivery.zomato_backend.repository.RestaurantRepository;
import com.food_delivery.zomato_backend.repository.UserRepository;
import com.food_delivery.zomato_backend.service.RestaurantService.RestaurantServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RestaurantTest {
    @Mock
    private RestaurantRepository restaurantRepository;
    @Mock
    private RestaurantMapper restaurantMapper;
    @InjectMocks
    private RestaurantServiceImpl restaurantService;
    @Mock
    private UserRepository userRepository;

    private Restaurant restaurant;
    private RestaurantRequestDto requestDto;
    private User ownerUser;
    private RestaurantResponseDto expectedResponse;

    @BeforeEach
    public void setUp(){
        // 1. Prepare test data
        ownerUser = User.builder()
                .id(1L)
                .email("test@example.com")
                .username("testuser")
                .password("encodedPassword")
                .address("123 Test St")
                .phoneNumber("1234567890")
                .role(Role.USER)
                .build();

        restaurant = Restaurant.builder()
                .id(1L)
                .name("Chinatown Restaurant")
                .address("123 Main Street")
                .location("New York")
                .latitude(40.7128)
                .longitude(-74.0060)
                .openingTime(LocalTime.parse("10:00"))
                .closingTime(LocalTime.parse("22:00"))
                .owner(ownerUser)
                .build();
        requestDto = RestaurantRequestDto.builder()
                .name("Chinatown Restaurant")
                .address("123 Main Street")
                .location("New York")
                .latitude(40.7128)
                .longitude(-74.0060)
                .ownerId(ownerUser.getId())
                .openingTime(LocalTime.parse("10:00"))
                .closingTime(LocalTime.parse("22:00"))
                .build();

        // Update owner role to OWNER since the service requires it
        ownerUser.setRole(Role.OWNER);
        // Create expected response
        expectedResponse = RestaurantResponseDto.builder()
                .id(restaurant.getId())
                .name(restaurant.getName())
                .address(restaurant.getAddress())
                .latitude(restaurant.getLatitude())
                .longitude(restaurant.getLongitude())
                .location(restaurant.getLocation())
                .phoneNumber(restaurant.getPhoneNumber())
                .email(restaurant.getEmail())
                .rating(restaurant.getRating())
                .openingTime(restaurant.getOpeningTime())
                .closingTime(restaurant.getClosingTime())
                .build();

    }
    @Test
    public void saveRestaurantTest(){
        // Arrange

        
        // Set up mocks
        when(userRepository.findById(ownerUser.getId())).thenReturn(Optional.of(ownerUser));
        when(restaurantRepository.existsByName(requestDto.getName())).thenReturn(false);
        when(restaurantMapper.toEntity(requestDto)).thenReturn(restaurant);
        when(restaurantRepository.save(restaurant)).thenReturn(restaurant);

        when(restaurantMapper.toRestaurantResponseDto(restaurant)).thenReturn(expectedResponse);

        // Act
        var response = restaurantService.createRestaurant(requestDto);

        // Assert
        assertNotNull(response);
        assertEquals(restaurant.getId(), response.getId());
        assertEquals(restaurant.getName(), response.getName());
        assertEquals(restaurant.getAddress(), response.getAddress());
        assertEquals(restaurant.getLocation(), response.getLocation());
        
        // Verify interactions
        verify(userRepository).findById(ownerUser.getId());
        verify(restaurantRepository).existsByName(requestDto.getName());
        verify(restaurantMapper).toEntity(requestDto);
        verify(restaurantRepository).save(restaurant);
        verify(restaurantMapper).toRestaurantResponseDto(restaurant);
    }

    @Test
    public void getRestaurantTest(){
        // Arrange
        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant));
        when(restaurantMapper.toRestaurantResponseDto(restaurant)).thenReturn(expectedResponse);

        // Act
        var response = restaurantService.getRestaurant(1L);

        // Assert
        assertNotNull(response);
        assertEquals(expectedResponse, response);
        assertEquals(restaurant.getId(), response.getId());
        assertEquals(restaurant.getName(), response.getName());
        assertEquals(restaurant.getAddress(), response.getAddress());
        assertEquals(restaurant.getLocation(), response.getLocation());

        // Verify interactions
        verify(restaurantRepository).findById(1L);
        verify(restaurantMapper).toRestaurantResponseDto(restaurant);

    }

    @Test
    public void getRestaurantNotFoundTest(){
        // Arrange
        when(restaurantRepository.findById(99L)).thenReturn(Optional.empty());

        //Act
        assertThrows(RestaurantNotFoundException.class, ()->{restaurantService.getRestaurant(99L);});

        // Verify interactions
        verify(restaurantRepository).findById(99L);

    }
    @Test
    public void getAllRestaurantsTest(){
        /// Arrange
        Restaurant restaurant2 = Restaurant.builder()
                .id(2L)
                .name("Chinatown Restaurant 2")
                .address("123 Main Street")
                .location("New York")
                .latitude(40.7128)
                .longitude(-74.0060)
                .openingTime(LocalTime.parse("10:00"))
                .closingTime(LocalTime.parse("22:00"))
                .owner(ownerUser)
                .build();
        RestaurantResponseDto expectedResponse2 = RestaurantResponseDto.builder()
                .id(restaurant2.getId())
                .name(restaurant2.getName())
                .address(restaurant2.getAddress())
                .latitude(restaurant2.getLatitude())
                .longitude(restaurant2.getLongitude())
                .location(restaurant2.getLocation())
                .phoneNumber(restaurant2.getPhoneNumber())
                .email(restaurant2.getEmail())
                .rating(restaurant2.getRating())
                .openingTime(restaurant2.getOpeningTime())
                .closingTime(restaurant2.getClosingTime())
                .build();
        /// Act
        var restaurants = List.of(restaurant, restaurant2);
        var dtos = List.of(expectedResponse, expectedResponse2);
        Pageable pageable = Pageable.unpaged();

        var restaurantPage = new PageImpl<>(restaurants,pageable,restaurants.size());
        /// Assert
        when(restaurantRepository.findAll(pageable)).thenReturn(restaurantPage);
        when(restaurantMapper.toRestaurantResponseDto(restaurant)).thenReturn(expectedResponse);
        when(restaurantMapper.toRestaurantResponseDto(restaurant2)).thenReturn(expectedResponse2);

        //Execute
        var result = restaurantService.getAllRestaurants(pageable);

        /// Assert
        assertNotNull(result);
        assertEquals(2,result.getContent().size());
        assertTrue("Result does not contain all expected dtos",result.getContent().containsAll(dtos));

        /// Verify interactions
        verify(restaurantRepository).findAll(pageable);
        verify(restaurantMapper).toRestaurantResponseDto(restaurant);
        verify(restaurantMapper).toRestaurantResponseDto(restaurant2);
    }

    @Test
    public void updateRestaurantTest(){
        /// Arrange
        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant));
        Restaurant updateRestaurant = Restaurant.builder()
                .id(1L)
                .name("Updated Restaurant")
                .phoneNumber("123-456-7890")
                .email("q4iNp@example.com")
                .address("Updated Address")
                .location("Updated Location")
                .latitude(40.7128)
                .longitude(-74.0060)
                .openingTime(LocalTime.parse("10:00"))
                .closingTime(LocalTime.parse("22:00"))
                .build();
        when(restaurantMapper.toRestaurantResponseDto(any(Restaurant.class))).thenReturn(
                RestaurantResponseDto.builder()
                        .id(1L)
                        .name("Updated Restaurant")
                        .phoneNumber("123-456-7890")
                        .email("q4iNp@example.com")
                        .address("Updated Address")
                        .location("Updated Location")
                        .latitude(40.7128)
                        .longitude(-74.0060)
                        .openingTime(LocalTime.parse("10:00"))
                        .closingTime(LocalTime.parse("22:00"))
                        .build()
        );
        when(restaurantRepository.save(any(Restaurant.class))).thenReturn(updateRestaurant);


        /// Act
        var response = restaurantService.updateRestaurant(1L, RestaurantRequestDto.builder()
                .name(updateRestaurant.getName())
                .phoneNumber(updateRestaurant.getPhoneNumber())
                .email(updateRestaurant.getEmail())
                .address(updateRestaurant.getAddress())
                .location(updateRestaurant.getLocation())
                .build()
        );

        /// Assert
        assertNotNull(response);
        assertEquals("Updated Restaurant", response.getName());
        assertEquals("123-456-7890", response.getPhoneNumber());
        assertEquals("q4iNp@example.com", response.getEmail());
        assertEquals("Updated Address", response.getAddress());
        assertEquals("Updated Location", response.getLocation());


        /// Verify interactions
        verify(restaurantRepository).findById(1L);
        verify(restaurantMapper).toRestaurantResponseDto(any(Restaurant.class));
        verify(restaurantRepository).save(any(Restaurant.class));

    }


    @Test
    public void deleteRestaurantTest(){
        /// Arrange
        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant));


        //Act
        restaurantService.deleteRestaurant(1L);

        /// verify instruction
        verify(restaurantRepository).findById(1L);
        verify(restaurantRepository).delete(any(Restaurant.class));

    }
    @Test
    public void deleteRestaurantThrowsWhenNotFound(){
        when(restaurantRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(RestaurantNotFoundException.class, () -> {
            restaurantService.deleteRestaurant(99L);
        });
        verify(restaurantRepository).findById(99L);
    }



}
