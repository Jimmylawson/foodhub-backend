package com.food_delivery.zomato_backend.controller;

import com.food_delivery.zomato_backend.controller.auth.AuthController;
import com.food_delivery.zomato_backend.dtos.authDto.AuthRequest;
import com.food_delivery.zomato_backend.dtos.authDto.AuthResponse;

import com.food_delivery.zomato_backend.service.UserService.UserServiceInterface;
import com.food_delivery.zomato_backend.service.auth.AuthService;

import org.junit.jupiter.api.Test;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)

@ContextConfiguration(classes = {AuthController.class, TestSecurityConfigBase.class})
public class AuthControllerTest extends BaseControllerTest {
    @MockBean
    private AuthService authService;

    @MockBean
    private UserServiceInterface userService;

    @Test
    @WithMockUser(authorities = {"ROLE_USER", "ROLE_ADMIN", "ROLE_OWNER"})
    public void loginTest() throws Exception {
        /// Given
        var request = AuthRequest.builder()
                .email("test@example.com")
                .password("password")
                .build();
        var response = AuthResponse.builder()
                .accessToken("token")
                .refreshToken("refreshToken")
                .tokenType("Bearer")
                .expiresIn(3600L)
                .build();

        //when then
        when(authService.authenticate(request)).thenReturn(response);
        mockMvc.perform(post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.access_token").value("token"))
                .andExpect(jsonPath("$.refresh_token").value("refreshToken"))
                .andExpect(jsonPath("$.token_type").value("Bearer"))
                .andExpect(jsonPath("$.expires_in").value(3600L));


    }
//    @Test
//    public void registerTest() throws Exception{
//        User user = User.builder()
//                .email("test@example.com")
//                .username("testuser")
//                .password("encodedPassword")
//                .address("123 Test St")
//                .phoneNumber("1234567890")
//                .role(Role.USER)
//                .build();
//
//        /// when the
//        when(authService.(user)).thenReturn(user);
//    }
}


