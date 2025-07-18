package com.food_delivery.zomato_backend.service.auth;



import com.food_delivery.zomato_backend.dtos.authDto.AuthRequest;
import com.food_delivery.zomato_backend.dtos.authDto.AuthResponse;



import com.food_delivery.zomato_backend.exceptions.ExceptionJwtException;
import com.food_delivery.zomato_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service class for handling authentication-related operations.
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomUserDetailService customUserDetailService;

    @Override
    public AuthResponse authenticate(AuthRequest request) {
        // Authenticate user
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // Get the authenticated username/email from the principal
        String username = authentication.getName();
        
        // Load the user details using CustomUserDetailService
        UserDetails userDetails = customUserDetailService.loadUserByUsername(username);
        
        // Get the app user for additional details
        var appUser = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));

        HashMap<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("userId", appUser.getId());
        extraClaims.put("username", userDetails.getUsername());
        extraClaims.put("authorities", userDetails.getAuthorities());

        String accessToken = jwtTokenService.generateToken(extraClaims, userDetails);
        String refreshToken = jwtTokenService.generateRefreshToken(userDetails);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .expiresIn(jwtTokenService.getExpirationTime() / 1000) // Convert to seconds
                .build();
    }

    @Override
    public AuthResponse refreshToken(String refreshToken) {
        String email = jwtTokenService.extractUsernameFromJwt(refreshToken);
       var user =  userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
       UserDetails userDetails = new UserDetailImpl(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                List.of(() -> "ROLE_" + user.getRole().name())
        );

        if (!jwtTokenService.isTokenValid(refreshToken,userDetails)) {
            throw new ExceptionJwtException("Invalid refresh token");
        }

        // Generate new tokens
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("userId", user.getId());

        String newAccessToken = jwtTokenService.generateToken(extraClaims, userDetails);
        String newRefreshToken = jwtTokenService.generateRefreshToken(userDetails);

        return AuthResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .expiresIn(jwtTokenService.getExpirationTime() / 1000)
                .build();
    }

}
