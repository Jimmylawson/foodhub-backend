package com.food_delivery.zomato_backend.entity;


import com.food_delivery.zomato_backend.enumTypes.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
@Builder
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false,unique = true)
    private String username;
    @Column(nullable = false,unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    private String address;
    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Order> orders;
    @OneToMany(mappedBy ="deliveryPerson", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Delivery> deliveries; //// This is the delivery for a user
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Restaurant> restaurants;


}
