package com.food_delivery.zomato_backend.controller;

import com.food_delivery.zomato_backend.dtos.MenuItemDtos.MenuItemRequestDto;
import com.food_delivery.zomato_backend.dtos.MenuItemDtos.MenuItemResponseDto;
import com.food_delivery.zomato_backend.service.MenuItemService.MenuItemServiceInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Menu Items",description = "Menu Items Managemnt APIs")
public class MenuItemController {
    private final MenuItemServiceInterface menuItemServiceInterface;

    /// Create endpoints
    @Operation(summary = "Create a new menu item")
    @ApiResponse(responseCode = "201", description = "Menu item created successfully")
    @PostMapping("/restaurants/menu-items")
    public ResponseEntity<MenuItemResponseDto> createMenuItem(@Valid @RequestBody MenuItemRequestDto menuItemRequestDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(menuItemServiceInterface.saveMenuItem(menuItemRequestDto));
    }


    /// Read endpoints
    @Operation(summary = "Get a menu item by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Menu item found successfully"),
            @ApiResponse(responseCode = "404", description = "Menu item not found")
    })
    @GetMapping("/restaurants/menu-items/public/{menuItemId}")
    public ResponseEntity<MenuItemResponseDto> getMenuItem(@PathVariable Long menuItemId) {
        return ResponseEntity.ok(menuItemServiceInterface.getMenuItem(menuItemId));
    }

    @Operation(summary = "Get all menu items by restaurant id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Menu items found successfully"),
            @ApiResponse(responseCode = "404", description = "Menu items not found")
    })
    @GetMapping("/restaurants/{restaurantId}/menu-items")
    public ResponseEntity<Page<MenuItemResponseDto>> getAllMenuItemsByRestaurants(@PathVariable Long restaurantId, @PageableDefault(size = 10, sort = "name") Pageable pageable){
        return ResponseEntity.ok(menuItemServiceInterface.getAllMenuItemByRestaurantId(restaurantId, pageable));
    }

    ///Update endpoints
    @Operation(summary = "Update a menu item by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Menu item found successfully"),
            @ApiResponse(responseCode = "404", description = "Menu item not found")
    })
    @PutMapping("/{menuItemId}")
    public ResponseEntity<MenuItemResponseDto> updateMenuItem(@PathVariable Long menuItemId, @Valid @RequestBody MenuItemRequestDto menuItemRequestDto){
        return ResponseEntity.ok(menuItemServiceInterface.updateMenuItem(menuItemId, menuItemRequestDto));
    }

    @Operation(summary = "Partially update a menu item by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Menu item found successfully"),
            @ApiResponse(responseCode = "404", description = "Menu item not found")
    })
    @PatchMapping("/{menuItemId}")
    public ResponseEntity<MenuItemResponseDto> partialUpdate(@PathVariable Long menuItemId, @Valid @RequestBody MenuItemRequestDto menuItemRequestDto){
        return ResponseEntity.ok(menuItemServiceInterface.updateMenuItem(menuItemId, menuItemRequestDto));
    }


    ///delete endpoints
    @Operation(summary = "Delete a menu item by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Menu item deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Menu item not found")
    })
    @DeleteMapping("/menu-items/{menuItemId}")
    public ResponseEntity<Void> deleteMenuItem(@PathVariable Long menuItemId){
        menuItemServiceInterface.deleteMenuItem(menuItemId);
        return ResponseEntity.noContent().build();
    }

}
