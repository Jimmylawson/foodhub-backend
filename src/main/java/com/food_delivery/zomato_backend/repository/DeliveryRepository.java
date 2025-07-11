package com.food_delivery.zomato_backend.repository;

import com.food_delivery.zomato_backend.entity.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
}
