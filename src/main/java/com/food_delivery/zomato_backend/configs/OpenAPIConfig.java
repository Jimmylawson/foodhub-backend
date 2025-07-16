package com.food_delivery.zomato_backend.configs;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Zomato Backend API",
                version = "v1",
                description = "API documentation for Zomato clone backend"
        ),
        servers = @Server(url = "http://localhost:8081", description = "Local Server")
)
public class OpenAPIConfig {

}
