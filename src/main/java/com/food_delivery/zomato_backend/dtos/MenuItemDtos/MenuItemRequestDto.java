package com.food_delivery.zomato_backend.dtos.MenuItemDtos;


import com.food_delivery.zomato_backend.enumTypes.Category;
import com.food_delivery.zomato_backend.enumTypes.ItemType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuItemRequestDto {
    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "Description is required")
    private String description;
    @NotBlank(message = "Price is required")
    private BigDecimal price;
    @NotNull(message="type is required")
    private ItemType type;

    @NotNull(message = "Restaurant ID is required")
    private Long restaurantId;
    @NotNull(message = "Order ID is required")
    private Long orderId;
}
