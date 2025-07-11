package com.food_delivery.zomato_backend.repository;

import com.food_delivery.zomato_backend.entity.MenuItem;
import com.food_delivery.zomato_backend.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
