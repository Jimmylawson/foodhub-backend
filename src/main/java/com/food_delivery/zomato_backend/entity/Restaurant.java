package com.food_delivery.zomato_backend.entity;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
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
    // For geolocation (required for nearby search)
    @Column(nullable = false)
    private String address;
    private String location;
    @Builder.Default
    private Double latitude = 0.0;
    @Column(nullable = false)
    @Builder.Default
    private Double longitude = 0.0;
    @Column(name="phone_number",unique = true)
    private String phoneNumber;
    @Column(name="email",unique = true)
    private String email;
    private BigDecimal rating;
    @Column(name="opening_time")
    private LocalTime openingTime;
    @Column(name="closing_time")
    private LocalTime closingTime;
    @ManyToOne
    @JoinColumn(name="owner_id")
    private User owner;

    @OneToMany(mappedBy = "restaurant",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<MenuItem> menuItems;


}
