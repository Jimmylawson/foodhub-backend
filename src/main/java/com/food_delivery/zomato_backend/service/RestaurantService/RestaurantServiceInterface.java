package com.food_delivery.zomato_backend.service.RestaurantService;

import com.food_delivery.zomato_backend.dtos.RestaurantDtos.RestaurantRequestDto;
import com.food_delivery.zomato_backend.dtos.RestaurantDtos.RestaurantResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RestaurantServiceInterface {
    RestaurantResponseDto createRestaurant(RestaurantRequestDto restaurantRequestDto);
    RestaurantResponseDto getRestaurant(Long id);
    Page<RestaurantResponseDto> getAllRestaurants(Pageable pageable);
    RestaurantResponseDto updateRestaurant(Long id, RestaurantRequestDto restaurantRequestDto);
    void deleteRestaurant(Long id);

    /// Business Operations
    List<RestaurantResponseDto> searchRestaurants(String query);
    List<RestaurantResponseDto> getNearByRestaurants(Double latitude,Double longitude, Double radiusKm);
    void updateRestaurantRating(Long id, Double rating);
    List<RestaurantResponseDto> getRestaurantsByRating(Double rating);
    List<RestaurantResponseDto> getRestaurantByOwner(Long ownerId);

}
