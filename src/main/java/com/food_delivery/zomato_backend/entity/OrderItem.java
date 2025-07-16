package com.food_delivery.zomato_backend.entity;


import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name="order_items")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIdentityInfo(
    generator = ObjectIdGenerators.PropertyGenerator.class,
    property = "id"
)
public class OrderItem extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="order_id")
    @JsonIgnoreProperties({"orderItems", "payment", "delivery"})
    private Order order;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="menu_item_id")
    private MenuItem menuItem;
    private Integer quantity = 1;
    private BigDecimal price;
}
