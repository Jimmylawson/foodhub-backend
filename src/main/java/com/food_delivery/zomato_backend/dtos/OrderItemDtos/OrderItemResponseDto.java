package com.food_delivery.zomato_backend.dtos.OrderItemDtos;

import com.fasterxml.jackson.annotation.*;
import com.food_delivery.zomato_backend.dtos.MenuItemDtos.MenuItemResponseDto;
import com.food_delivery.zomato_backend.dtos.OrderDtos.OrderResponseDto;
import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(
    generator = ObjectIdGenerators.PropertyGenerator.class,
    property = "id"
)
public class OrderItemResponseDto {
    private Long id;
    @Builder.Default
    private Integer quantity = 1;
    private BigDecimal price;

    @JsonIgnoreProperties({"orderItems"})
    private MenuItemResponseDto menuItem;
    
    @JsonIgnoreProperties({"items", "payment", "delivery"})
    private OrderResponseDto order;

    @JsonProperty("totalPrice")
    public BigDecimal getTotalPrice() {
        if (price == null || quantity == null) {
            return BigDecimal.ZERO;
        }
        return price.multiply(BigDecimal.valueOf(quantity));
    }
}
