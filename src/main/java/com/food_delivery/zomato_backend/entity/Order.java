package com.food_delivery.zomato_backend.entity;


import com.fasterxml.jackson.annotation.*;
import com.food_delivery.zomato_backend.enumTypes.OrderType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;


@Entity
@Setter @Getter
@Table(name="orders")
@AllArgsConstructor @NoArgsConstructor
@Builder
@JsonIdentityInfo(
    generator = ObjectIdGenerators.PropertyGenerator.class,
    property = "id"
)
public class Order extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private OrderType type; /// delivery or pickup
    private BigDecimal price;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="user_id")
    private User user;
    @OneToMany(mappedBy ="order",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<OrderItem> orderItems;
    @OneToOne(mappedBy = "order",cascade = CascadeType.ALL,orphanRemoval = true)
    private Payment payment;
    @OneToOne(mappedBy = "order",cascade = CascadeType.ALL,orphanRemoval = true)
    private Delivery delivery;
}
