package com.food_delivery.zomato_backend.dtos.OrderItemDtos;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemRequestDto {

    @NotNull(message = "Quantity is required")
    @Positive(message = "Quantity must be positive")
   private Integer quantity;
    @NotNull(message = "Menu item id is required")
    @Positive(message = "Menu item id must be positive")
    private Long menuItemId;
}
