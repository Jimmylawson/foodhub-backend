package com.food_delivery.zomato_backend.controller;

import com.food_delivery.zomato_backend.dtos.RestaurantDtos.RestaurantRequestDto;
import com.food_delivery.zomato_backend.dtos.RestaurantDtos.RestaurantResponseDto;
import com.food_delivery.zomato_backend.dtos.UserDtos.UserBasicDto;
import com.food_delivery.zomato_backend.service.RestaurantService.RestaurantServiceInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RestaurantController.class)
@ContextConfiguration(classes = {RestaurantController.class, TestSecurityConfigBase.class})
public class RestaurantControllerTest extends BaseControllerTest {
    @MockBean
    private RestaurantServiceInterface restaurantService;

    private RestaurantRequestDto restaurantRequestDto;
    private RestaurantResponseDto restaurantResponseDto;

    @BeforeEach
    public void setUp() {
        restaurantRequestDto = RestaurantRequestDto.builder()

                .name("Test Restaurant")
                .address("Test Address")
                .ownerId(1L)
                .location("Test Location")
                .latitude(40.7128)
                .longitude(-74.0060)
                .phoneNumber("1234567890")
                .rating(BigDecimal.valueOf(4.5))
                .openingTime(LocalTime.parse("10:00"))
                .closingTime(LocalTime.parse("22:00"))
                .build();

        // Create a UserBasicDto for the owner
        UserBasicDto ownerDto = UserBasicDto.builder()
                .id(1L)
                .username("testuser")
                .email("test@example.com")
                .build();

        restaurantResponseDto = RestaurantResponseDto.builder()
                .id(100L)
                .name(restaurantRequestDto.getName())
                .address(restaurantRequestDto.getAddress())
                .location(restaurantRequestDto.getLocation())
                .latitude(restaurantRequestDto.getLatitude())
                .longitude(restaurantRequestDto.getLongitude())
                .openingTime(restaurantRequestDto.getOpeningTime())
                .closingTime(restaurantRequestDto.getClosingTime())
                .phoneNumber("1234567890")
                .email("test@example.com")
                .rating(BigDecimal.valueOf(4.5))
                .owner(ownerDto)
                .menuItems(Collections.emptyList())
                .build();

    }

    @Test
    @WithMockUser(authorities = {"ROLE_ADMIN", "ROLE_OWNER"})
    public void testCreateRestaurant() throws Exception {

        when(restaurantService.createRestaurant(any(RestaurantRequestDto.class))).thenReturn(restaurantResponseDto);

        mockMvc.perform(post("/api/v1/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(restaurantRequestDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(restaurantResponseDto.getId()))
                .andExpect(jsonPath("$.name").value(restaurantResponseDto.getName()))
                .andExpect(jsonPath("$.address").value(restaurantResponseDto.getAddress()))
                .andExpect(jsonPath("$.location").value(restaurantResponseDto.getLocation()))
                .andExpect(jsonPath("$.latitude").value(restaurantResponseDto.getLatitude()))
                .andExpect(jsonPath("$.longitude").value(restaurantResponseDto.getLongitude()))
                .andExpect(jsonPath("$.openingTime").value(restaurantResponseDto.getOpeningTime().toString() + ":00"))
                .andExpect(jsonPath("$.closingTime").value(restaurantResponseDto.getClosingTime().toString() + ":00"))
                .andExpect(jsonPath("$.phoneNumber").value(restaurantResponseDto.getPhoneNumber()))
                .andExpect(jsonPath("$.email").value(restaurantResponseDto.getEmail()))
                .andExpect(jsonPath("$.rating").value(restaurantResponseDto.getRating().toString()))
                .andExpect(jsonPath("$.owner.id").value(restaurantResponseDto.getOwner().getId()))
                .andExpect(jsonPath("$.owner.username").value(restaurantResponseDto.getOwner().getUsername()))
                .andExpect(jsonPath("$.owner.email").value(restaurantResponseDto.getOwner().getEmail()))
                .andExpect(jsonPath("$.menuItems").isEmpty());



    }

    @Test
    public void testGetRestaurant() throws Exception {
        when(restaurantService.getRestaurant(any(Long.class))).thenReturn(restaurantResponseDto);

        mockMvc.perform(get("/api/v1/restaurants/100")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(restaurantResponseDto.getId()))
                .andExpect(jsonPath("$.name").value(restaurantResponseDto.getName()))
                .andExpect(jsonPath("$.address").value(restaurantResponseDto.getAddress()))
                .andExpect(jsonPath("$.location").value(restaurantResponseDto.getLocation()))
                .andExpect(jsonPath("$.latitude").value(restaurantResponseDto.getLatitude()))
                .andExpect(jsonPath("$.longitude").value(restaurantResponseDto.getLongitude()));

    }

    @Test
    public void testGetAllRestaurants() throws Exception {
        when(restaurantService.getAllRestaurants(any(Pageable.class))).thenReturn(new PageImpl<>(Collections.emptyList()));

        mockMvc.perform(get("/api/v1/restaurants")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }



}