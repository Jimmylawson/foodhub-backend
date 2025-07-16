package com.food_delivery.zomato_backend.entity;


import com.fasterxml.jackson.annotation.*;
import com.food_delivery.zomato_backend.enumTypes.PaymentType;
import com.food_delivery.zomato_backend.enumTypes.Status;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Setter
@Getter
@Table(name="payments")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIdentityInfo(
    generator = ObjectIdGenerators.PropertyGenerator.class,
    property = "id"
)
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name="order_id", unique = true)
    @JsonIgnoreProperties({"orderItems", "payment", "delivery"})
    private Order order;
    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Column(name="transaction_id",unique = true)
    private String transactionId;
    @Column(name="paid_at",updatable = false)
    @CreationTimestamp
    private LocalDateTime paidAt;

}
