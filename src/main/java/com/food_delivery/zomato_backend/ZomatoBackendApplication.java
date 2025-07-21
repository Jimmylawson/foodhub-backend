package com.food_delivery.zomato_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@ComponentScan({"com.food_delivery.zomato_backend", 
              "com.food_delivery.zomato_backend.service.auth"})
@EnableMethodSecurity
@EnableCaching
public class ZomatoBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZomatoBackendApplication.class, args);
	}

}
