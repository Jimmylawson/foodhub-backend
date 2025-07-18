package com.food_delivery.zomato_backend.Service;


import com.food_delivery.zomato_backend.dtos.authDto.AuthRequest;
import com.food_delivery.zomato_backend.entity.User;
import com.food_delivery.zomato_backend.enumTypes.Role;
import com.food_delivery.zomato_backend.repository.UserRepository;
import com.food_delivery.zomato_backend.service.auth.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.Extensions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {


    @Mock
    private UserRepository userRepository;
    @Mock
    private JwtTokenService jwtTokenService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private CustomUserDetailService customUserDetailService;
    private User testUser;
    @InjectMocks
    private AuthServiceImpl authService; ///Testing the actual implementation


    @BeforeEach
    void setUp(){
        testUser = User.builder()
                .id(1L)
                .email("test@example.com")
        .password("encodedPassword")
                .role(Role.USER)
                .build();

    }
    @Test
    public void AuthenticationSuccess(){
        UserDetails userDetails = new UserDetailImpl(
                testUser.getId(),
                testUser.getEmail(),
                testUser.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_" + testUser.getRole().name()))
        );
        AuthRequest request = AuthRequest.builder()
                .email(testUser.getEmail())
                .password(testUser.getPassword())
                .build();
        /// Create a proper Authentication object with the userDetails as principal
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                testUser.getEmail(),
                testUser.getPassword(),
                userDetails.getAuthorities());

        /// Set up the mocks
        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        // Add this line to mock the customUserDetailService
        when(customUserDetailService.loadUserByUsername(testUser.getEmail()))
                .thenReturn(userDetails);
        when(userRepository.findByEmail(testUser.getEmail())).thenReturn(Optional.of(testUser));
        when(jwtTokenService.generateToken(anyMap(),eq(userDetails))).thenReturn("access-token");
        when(jwtTokenService.generateRefreshToken(userDetails)).thenReturn("refresh-token");
        when(jwtTokenService.getExpirationTime()).thenReturn(86400000L);

        /// Execute
        var response = authService.authenticate(request);

        /// Assert
        assertNotNull(response);
        assertEquals("access-token",response.getAccessToken());
        assertEquals("refresh-token",response.getRefreshToken());
        assertEquals(86400L,response.getExpiresIn().longValue());

    }

    @Test
    public void AuthenticationFailsWhenUserNotFound() {
        var request = AuthRequest.builder()
                .email("invalid@exmaple.com")
                .password("wrongPassword")
                .build();

        when(authenticationManager.authenticate(any())).thenThrow(
                new BadCredentialsException("Invalid username or password"));

        assertThrows(BadCredentialsException.class, () -> {
            authService.authenticate(request);

        });
    }

}
