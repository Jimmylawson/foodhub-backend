package com.food_delivery.zomato_backend.repository;

import com.food_delivery.zomato_backend.entity.MenuItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    Page<MenuItem> findByRestaurantId(Long restaurantId, Pageable pageable);
}
