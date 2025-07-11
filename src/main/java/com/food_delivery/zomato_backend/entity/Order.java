package com.food_delivery.zomato_backend.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@Setter @Getter
@Table(name="orders")
@AllArgsConstructor @NoArgsConstructor
@Builder
public class Order extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type; /// delivery orr pickup
    private Double price;
    @ManyToOne
    @JoinColumn(name ="user_id")
    private User user;
    @OneToMany(mappedBy ="order",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<OrderItem> orderItems;
    @OneToOne(mappedBy = "order",cascade = CascadeType.ALL,orphanRemoval = true)
    private Payment payment;
    @OneToOne(mappedBy = "order",cascade = CascadeType.ALL,orphanRemoval = true)
    private Delivery delivery;
}
