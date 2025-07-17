package com.food_delivery.zomato_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.food_delivery.zomato_backend", 
              "com.food_delivery.zomato_backend.service.auth"})
public class ZomatoBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZomatoBackendApplication.class, args);
	}

}
