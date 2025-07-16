package com.food_delivery.zomato_backend.configs.securityfiles;

import com.food_delivery.zomato_backend.exceptions.InvalidCredentialsException;
import com.food_delivery.zomato_backend.service.auth.CustomUserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FoodHubAuthenticationProvider implements AuthenticationProvider {
    private final CustomUserDetailService customUserDetailService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        var user = customUserDetailService.loadUserByUsername(username);

        if(!passwordEncoder.matches(password,user.getPassword()))
            throw new InvalidCredentialsException("Invalid username or password");


        return new UsernamePasswordAuthenticationToken(
                    username,
                    password,
                    user.getAuthorities()
            );

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
