package com.food_delivery.zomato_backend.entity;


import com.fasterxml.jackson.annotation.*;
import com.food_delivery.zomato_backend.enumTypes.DeliveryStatus;
import com.food_delivery.zomato_backend.enumTypes.Mode;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;

@Entity
@Setter
@Getter
@Table(name="delivery")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIdentityInfo(
    generator = ObjectIdGenerators.PropertyGenerator.class,
    property = "id"
)
public class Delivery extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_person_id")
    private User deliveryPerson;
    @Enumerated(EnumType.STRING)
    @Column(name="delivery_mode")

    private Mode mode;
    @OneToOne
    @JoinColumn(name = "order_id", unique = true)
    @JsonIgnoreProperties({"orderItems", "payment", "delivery"})
    private Order order;
    @Column(name = "delivery_address", nullable = false)
    private String address;
    @Column(name="delivery_time")
    private LocalTime deliveryTime;
    @Column(name="pickup_time")
    private LocalTime pickupTime;
    @Enumerated(EnumType.STRING)
    @Column(name="delivery_status")
    @Builder.Default
    private DeliveryStatus deliveryStatus = DeliveryStatus.PENDING;



}
