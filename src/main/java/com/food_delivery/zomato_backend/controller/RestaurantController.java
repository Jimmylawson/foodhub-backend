package com.food_delivery.zomato_backend.controller;

import com.food_delivery.zomato_backend.dtos.RestaurantDtos.RestaurantRequestDto;
import com.food_delivery.zomato_backend.dtos.RestaurantDtos.RestaurantResponseDto;
import com.food_delivery.zomato_backend.service.RestaurantService.RestaurantServiceInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/v1/restaurants")
@Tag(name="Restaurants",description="Restaurants Management APIs ")
@RequiredArgsConstructor
public class RestaurantController {
    private final RestaurantServiceInterface restaurantService;


    @Operation(summary = "Create a new restaurant")
    @ApiResponse(responseCode = "201", description = "Restaurant created successfully")
   @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'OWNER')")
    public ResponseEntity<RestaurantResponseDto> createRestaurant(@Valid @RequestBody RestaurantRequestDto   restaurantRequestDto){
       return ResponseEntity.status(201).body(restaurantService.createRestaurant(restaurantRequestDto));
   }
    /// Read endpoints
    @Operation(summary ="Get a restaurant by id")
    @ApiResponse(responseCode = "200", description = "Restaurant found successfully")
    @ApiResponse(responseCode = "404", description = "Restaurant not found")
    @GetMapping("/{restaurantId}")
    public ResponseEntity<RestaurantResponseDto> getRestaurant(@PathVariable Long restaurantId){
        return ResponseEntity.ok(restaurantService.getRestaurant(restaurantId));
    }

    @Operation(summary = "Get all restaurants")
    @ApiResponse(responseCode = "200", description = "Restaurants found successfully")
    @ApiResponse(responseCode = "404", description = "Restaurants not found")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping()
    public ResponseEntity<Page<RestaurantResponseDto>> getAllRestaurants(@PageableDefault(size = 10, sort = "name") Pageable pageable){
        return ResponseEntity.ok(restaurantService.getAllRestaurants(pageable));
    }

    ///Update endpoints
    @Operation(summary = "Update a restaurant by id")
    @ApiResponse(responseCode = "200", description = "Restaurant found successfully")
    @ApiResponse(responseCode = "404", description = "Restaurant not found")
    @PreAuthorize("hasAnyRole('ADMIN', 'OWNER')")
    @PutMapping("/{restaurantId}")
    public ResponseEntity<RestaurantResponseDto> updateRestaurant(@PathVariable Long restaurantId, @Valid @RequestBody RestaurantRequestDto restaurantRequestDto){
        return ResponseEntity.ok(restaurantService.updateRestaurant(restaurantId, restaurantRequestDto));
    }

    @Operation(summary = "Partially update a restaurant by id")
    @ApiResponse(responseCode = "200", description = "Restaurant found successfully")
    @ApiResponse(responseCode = "404", description = "Restaurant not found")
    @PreAuthorize("hasAnyRole('ADMIN', 'OWNER')")
    @PatchMapping("/{restaurantId}")
    public ResponseEntity<RestaurantResponseDto> partialUpdate(@PathVariable Long restaurantId, @Valid @RequestBody RestaurantRequestDto restaurantRequestDto){
        return ResponseEntity.ok(restaurantService.updateRestaurant(restaurantId, restaurantRequestDto));
    }

    ///delete endpoints
    @Operation(summary = "Delete a restaurant by id")
    @ApiResponse(responseCode = "204", description = "Restaurant deleted successfully")
    @ApiResponse(responseCode = "404", description = "Restaurant not found")
    @PreAuthorize("hasAnyRole('ADMIN', 'OWNER')")
    @DeleteMapping("/{restaurantId}")
    public ResponseEntity<String> deleteRestaurant(@PathVariable Long restaurantId){
        restaurantService.deleteRestaurant(restaurantId);
        return ResponseEntity.status(204).body("Restaurant with id " + restaurantId + " deleted successfully");
    }
}
