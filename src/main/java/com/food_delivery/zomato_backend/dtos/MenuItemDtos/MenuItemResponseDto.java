package com.food_delivery.zomato_backend.dtos.MenuItemDtos;

import com.food_delivery.zomato_backend.enumTypes.Category;
import com.food_delivery.zomato_backend.enumTypes.ItemType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuItemResponseDto {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private ItemType type;
    private Long restaurantId;
    private String restaurantName;

}
