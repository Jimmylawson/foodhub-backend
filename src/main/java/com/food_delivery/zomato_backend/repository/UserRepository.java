package com.food_delivery.zomato_backend.repository;

import com.food_delivery.zomato_backend.entity.User;
import jakarta.validation.constraints.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository  extends JpaRepository<User, Long> {
    boolean existsByEmail(@Email(message = "Email is not valid") String email);
    /// To find the user name
    Optional<User> findByUsername(String username);
   Optional<User>findByEmail(String email);
}
