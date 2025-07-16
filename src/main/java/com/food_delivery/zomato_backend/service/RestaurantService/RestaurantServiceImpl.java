package com.food_delivery.zomato_backend.service.RestaurantService;

import com.food_delivery.zomato_backend.dtos.RestaurantDtos.RestaurantRequestDto;
import com.food_delivery.zomato_backend.dtos.RestaurantDtos.RestaurantResponseDto;
import com.food_delivery.zomato_backend.entity.Restaurant;
import com.food_delivery.zomato_backend.enumTypes.Role;
import com.food_delivery.zomato_backend.exceptions.DuplicateRestaurantException;
import com.food_delivery.zomato_backend.exceptions.restaurantException.RestaurantNotFoundException;
import com.food_delivery.zomato_backend.exceptions.users.UserNotFoundException;
import com.food_delivery.zomato_backend.mapper.RestaurantMapper;
import com.food_delivery.zomato_backend.repository.RestaurantRepository;
import com.food_delivery.zomato_backend.repository.UserRepository;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantServiceInterface {
    private final RestaurantMapper restaurantMapper;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;
    /// Find the restaurant
    private Restaurant getRestaurantOrThrowError(Long id) {
        return restaurantRepository.findById(id)
                .orElseThrow(() -> new RestaurantNotFoundException(id));
    }

    @Override
    public RestaurantResponseDto createRestaurant(RestaurantRequestDto restaurantRequestDto) {
        var owner = userRepository.findById(restaurantRequestDto.getOwnerId())
                .orElseThrow(() -> new UserNotFoundException(restaurantRequestDto.getOwnerId()));
        if(!owner.getRole().equals(Role.OWNER))
            throw new ValidationException("Only users with OWNER role can create restaurants");
        if (restaurantRepository.existsByName(restaurantRequestDto.getName())) {
            throw new DuplicateRestaurantException("Restaurant with name " + restaurantRequestDto.getName() + " already exists");
        }

        var restaurant = restaurantMapper.toEntity(restaurantRequestDto);
        restaurant.setOwner(owner);
        var savedRestaurant = restaurantRepository.save(restaurant);
        return restaurantMapper.toRestaurantResponseDto(savedRestaurant);
    }

    @Override
    public Page<RestaurantResponseDto> getAllRestaurants(Pageable pageable) {
       return restaurantRepository.findAll(pageable)
               .map(restaurantMapper::toRestaurantResponseDto);

    }



    @Override
    public RestaurantResponseDto getRestaurant(Long id) {
        return restaurantMapper.toRestaurantResponseDto(getRestaurantOrThrowError(id));
    }

    @Override
    public RestaurantResponseDto updateRestaurant(Long id, RestaurantRequestDto restaurantRequestDto) {
        /// Get existing restaurant
        Restaurant restaurant = getRestaurantOrThrowError(id);
        /// Validate business hours if they're updated
        if(restaurantRequestDto.getOpeningTime()!= null ||
        restaurantRequestDto.getClosingTime() != null) {
            var openingTime = restaurantRequestDto.getOpeningTime() !=null
                    ?restaurantRequestDto.getOpeningTime()
                    :restaurant.getOpeningTime();
            var closingTime = restaurantRequestDto.getClosingTime() !=null
                    ?restaurantRequestDto.getClosingTime()
                    :restaurant.getClosingTime();
            validateBusinessHours(openingTime, closingTime);
        }

        /// Update fields from Dto to existing restaurants
        restaurantMapper.updateRestaurantFromDto(restaurantRequestDto, restaurant);

        /// Update the restaurant
        restaurant.setUpdatedAt(LocalDateTime.now());

        /// Save and return the restaurant
        return restaurantMapper.toRestaurantResponseDto(restaurantRepository.save(restaurant));

    }
    private void validateBusinessHours(LocalTime openingTime, LocalTime closingTime) {
        if (openingTime != null && closingTime != null && !openingTime.isBefore(closingTime)) {
            throw new IllegalArgumentException("Opening time must be before closing time");
        }
    }

    @Override
    public void deleteRestaurant(Long id) {
        var restaurant = getRestaurantOrThrowError(id);
        restaurantRepository.delete(restaurant);

    }

    /// Business Operations
    @Override
    public List<RestaurantResponseDto> searchRestaurants(String query) {
        return restaurantRepository.searchRestaurants(query)
                .stream()
                .map(restaurantMapper::toRestaurantResponseDto)
                .toList();
    }

    @Override
    public List<RestaurantResponseDto> getNearByRestaurants(Double latitude, Double longitude, Double radiusKm) {
        return restaurantRepository.findNearbyRestaurants(latitude, longitude, radiusKm)
                .stream()
                .map(restaurantMapper::toRestaurantResponseDto)
                .toList();

    }

    @Override
    public void updateRestaurantRating(Long id, Double rating) {
        var restaurant = getRestaurantOrThrowError(id);
        restaurant.setRating(BigDecimal.valueOf(rating));
        restaurantRepository.save(restaurant);

    }

    @Override
    public List<RestaurantResponseDto> getRestaurantsByRating(Double rating) {
        if(rating == null || rating < 0 || rating > 5){
            throw new IllegalArgumentException("Rating must be between 0 and 5");
        }

        return  restaurantRepository
                .findByRatingGreaterThanEqual(BigDecimal.valueOf(rating))
                .stream()
                .map(restaurantMapper::toRestaurantResponseDto)
                .toList();
    }

    @Override
    public List<RestaurantResponseDto> getRestaurantByOwner(Long ownerId) {
       var owner  = restaurantRepository.findByOwnerId(ownerId);

       if(owner.isEmpty())
           throw new UserNotFoundException(ownerId);

       return owner
               .stream()
               .map(restaurantMapper::toRestaurantResponseDto)
               .toList();

    }
}
