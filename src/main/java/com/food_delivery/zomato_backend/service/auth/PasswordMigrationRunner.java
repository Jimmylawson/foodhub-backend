package com.food_delivery.zomato_backend.service.auth;

import com.food_delivery.zomato_backend.entity.User;
import com.food_delivery.zomato_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
//
//@Component
//@RequiredArgsConstructor
//@Slf4j
//public class PasswordMigrationRunner implements CommandLineRunner {
//    private final PasswordEncoder passwordEncoder;
//    private final UserRepository userRepository;
//
//
//
//    @Override
//    public void run(String... args) throws Exception {
//        log.info("Starting password migration process...");
//        int migratedCount = 0;
//
//        List<User> users = userRepository.findAll();
//        log.info("Found {} users to process", users.size());
//
//        for (User user : users) {
//            String password = user.getPassword();
//            try {
//                if (password != null && !password.startsWith("$2a$")) {
//                    user.setPassword(passwordEncoder.encode(password));
//                    userRepository.save(user);
//                    migratedCount++;
//                    log.debug("Successfully migrated password for user: {}", user.getUsername());
//                }
//            } catch (Exception e) {
//                log.error("Failed to migrate password for user: {}", user.getUsername(), e);
//            }
//        }
//
//        log.info("Password migration process completed. Migrated {} users", migratedCount);
//    }
//}
