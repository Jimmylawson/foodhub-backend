package com.food_delivery.zomato_backend.configs.securityfiles;

import com.food_delivery.zomato_backend.service.auth.CustomUserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityChain {
    private final CustomUserDetailService customUserDetailService;
    private final FoodHubAuthenticationProvider authenticationProvider;
    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf->csrf.disable())
                .authenticationProvider(authenticationProvider)
                .authorizeHttpRequests(request -> {
                    request.requestMatchers(
                            /// public endpoints
                            "/api/v1/auth/**",
                            "/api/v1/public/**",
                            "/v3/api-docs/**",
                            "/swagger-ui/**",
                            "/swagger-ui.html"

                    ).permitAll();
                    /// All other endpoints requiring authentication
                    request.requestMatchers(HttpMethod.GET, "/api/v1/restaurants/menu-items/public/**").permitAll()
                            .requestMatchers("/api/v1/deliveries/**").hasAnyRole("ADMIN","DELIVERY")
                            .requestMatchers("/api/v1/restaurants/**").hasAnyRole("ADMIN","OWNER")
                            .requestMatchers("/api/v1/orders/**").hasAnyRole("ADMIN","USER")
                            .requestMatchers("/api/v1/order-items/**").hasAnyRole("ADMIN","USER")
                            .requestMatchers("/api/v1/menu-items/**").hasAnyRole("ADMIN","OWNER")
                            .anyRequest().authenticated();

                })

                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults());
        return http.build();


    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
