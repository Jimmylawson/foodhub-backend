package com.ems_backend.jimmydev.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Employee Management System API")
                        .version("1.0.0")
                        .description("API for managing employees.")
                        .contact(
                                new Contact()
                                        .name("Jim Osei")
                                        .email("jimosei72@gmail.com")
                                        .url("https://github.com/jimmylawson")

                        )

                );
    }
}
