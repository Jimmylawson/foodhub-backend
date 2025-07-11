package com.food_delivery.zomato_backend.dtos.OrderItemDtos;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.food_delivery.zomato_backend.dtos.MenuItemDtos.MenuItemResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemResponseDto {
    private Long id;
    @Builder.Default
    private Integer quantity = 1;
    private BigDecimal price;

    private MenuItemResponseDto menuItem;
    @JsonProperty("totalPrice")
    public BigDecimal getTotalPrice() {
        if (price == null || quantity == null) {
            return BigDecimal.ZERO;
        }
        return price.multiply(BigDecimal.valueOf(quantity));
    }
}
