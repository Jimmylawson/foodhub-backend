package com.food_delivery.zomato_backend.repository;

import com.food_delivery.zomato_backend.entity.User;
import jakarta.validation.constraints.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository  extends JpaRepository<User, Long> {
    boolean existsByEmail(@Email(message = "Email is not valid") String email);
}
