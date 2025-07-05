package com.jimmydev.blog_api.repository;

import com.jimmydev.blog_api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
