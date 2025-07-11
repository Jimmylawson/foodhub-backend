package com.food_delivery.zomato_backend.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name="restaurants")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Restaurant extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String address;
    private String location;
    @Column(name="phone_number",unique = true)
    private String phoneNumber;
    @Column(name="email",unique = true)
    private String email;
    private Integer rating;
    @Column(name="opening_time")
    private LocalTime openingTime;
    @Column(name="closing_time")
    private LocalTime closingTime;
    @ManyToOne
    @JoinColumn(name="owner_id")
    private User owner;

    @OneToMany(mappedBy = "restaurant",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<MenuItem> menuItems;

    @OneToMany(mappedBy = "restaurant",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Delivery> deliveries;

}
