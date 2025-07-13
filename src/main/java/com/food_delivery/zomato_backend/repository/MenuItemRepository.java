package com.food_delivery.zomato_backend.repository;

import com.food_delivery.zomato_backend.entity.MenuItem;
import com.food_delivery.zomato_backend.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    List<MenuItem> findByRestaurant(Restaurant restaurant);
}
