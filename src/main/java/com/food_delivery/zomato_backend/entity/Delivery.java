package com.food_delivery.zomato_backend.entity;


import com.food_delivery.zomato_backend.enumTypes.DeliveryStatus;
import com.food_delivery.zomato_backend.enumTypes.Mode;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Setter
@Getter
@Table(name="delivery")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Delivery extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "delivery_person_id")
    private User deliveryPerson;

    @OneToOne
    @JoinColumn(name = "order_id",unique = true)
    private Order order;
    @Column(name = "delivery_address", nullable = false)
    private String address;
    @Column(name="delivery_time")
    private LocalTime deliveryTime;
    @Enumerated(EnumType.STRING)
    @Column(name="delivery_status")
    private DeliveryStatus deliveryStatus;

}
