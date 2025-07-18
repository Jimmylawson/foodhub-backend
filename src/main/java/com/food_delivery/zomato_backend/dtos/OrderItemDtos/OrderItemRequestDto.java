package com.food_delivery.zomato_backend.dtos.OrderItemDtos;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Builder
@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemRequestDto {
    @NotNull(message = "Order id is required")
    @Positive(message = "Order id must be positive")
    private Long orderId;
    @NotNull(message = "Quantity is required")
    @Positive(message = "Quantity must be positive")
   private Integer quantity;
    @NotNull(message = "Menu item id is required")
    @Positive(message = "Menu item id must be positive")
    private Long menuItemId;
}
