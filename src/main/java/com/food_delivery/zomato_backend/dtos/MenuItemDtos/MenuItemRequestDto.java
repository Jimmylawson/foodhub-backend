package com.food_delivery.zomato_backend.dtos.MenuItemDtos;



import com.food_delivery.zomato_backend.enumTypes.ItemType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MenuItemRequestDto {
    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "Description is required")
    private String description;
    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.01", message = "Price must be greater than 0")
    private BigDecimal price;
    @NotNull(message="type is required")
    private ItemType type;
    @NotNull(message = "Restaurant ID is required")
    private Long restaurantId;

}
