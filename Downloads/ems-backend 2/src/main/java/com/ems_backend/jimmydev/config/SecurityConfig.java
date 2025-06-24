package com.ems_backend.jimmydev.config;

import com.ems_backend.jimmydev.filters.JwtAuthFilter;
import com.ems_backend.jimmydev.service.EmployeeDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthFilter jwtAuthFilter) throws Exception{
          http
                  .csrf(c->c.disable())
                    .cors(c->c.disable())
                  .authorizeHttpRequests(request-> request
                          .requestMatchers(HttpMethod.POST, "/api/v1/employees/login","/api/v1/employees").permitAll()
                          .requestMatchers(HttpMethod.GET, "/api/v1/employees/{id}").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                            .requestMatchers(HttpMethod.PATCH, "/api/v1/employees/{id}").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                          .requestMatchers("/api/v1/employees/**").hasAuthority("ROLE_ADMIN")
                          .anyRequest().authenticated())
                  .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                  .sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                  .formLogin(Customizer.withDefaults())
                  .httpBasic(Customizer.withDefaults());


          return http.build();

    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}