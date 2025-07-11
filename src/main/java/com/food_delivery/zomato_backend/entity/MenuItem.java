package com.food_delivery.zomato_backend.entity;

import com.food_delivery.zomato_backend.enumTypes.Category;
import com.food_delivery.zomato_backend.enumTypes.ItemType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name="menu_items")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name; ///Name of the item
    private String description; ///Description of the item
//    private Integer quantity; ///Quantity of the item
    private BigDecimal price; ///Price of the item
    @Enumerated(EnumType.STRING)
    private ItemType type; /// Veg, Non-Veg, Vegan
    @ManyToOne
    @JoinColumn(name="restaurant_id")
    private Restaurant restaurant;

    @OneToMany(mappedBy = "menuItem",cascade = CascadeType.ALL,orphanRemoval = true)
    public List<OrderItem> orderItems;
}
