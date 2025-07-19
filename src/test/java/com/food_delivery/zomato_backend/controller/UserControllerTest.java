package com.food_delivery.zomato_backend.controller;

import com.food_delivery.zomato_backend.dtos.UserDtos.UserRequestDto;
import com.food_delivery.zomato_backend.dtos.UserDtos.UserResponseDto;
import com.food_delivery.zomato_backend.enumTypes.Role;
import com.food_delivery.zomato_backend.service.UserService.UserServiceInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(UserController.class)
@ContextConfiguration(classes = {UserController.class, TestSecurityConfigBase.class})
public class UserControllerTest extends BaseControllerTest {

    @MockBean
    private UserServiceInterface userService;

    private UserRequestDto userRequestDto;
    private UserResponseDto userResponseDto;


    @BeforeEach
    public void setUp() {

        userRequestDto = UserRequestDto.builder()
                .username("testuser")
                .email("test@example.com")
                .password("encodedPassword")
                .address("123 Test St")
                .phoneNumber("1234567890")
                .role(Role.USER)
                .build();

        userResponseDto = UserResponseDto.builder()
                .id(1L)
                .username("testuser")
                .email("test@example.com")
                .address("123 Test St")
                .phoneNumber("1234567890")
                .role(Role.USER)
                .build();


    }

    @Test
    @WithMockUser(roles="ADMIN")
    public void testCreateUser() throws Exception {
        /// Arrange
        when(userService.createUser(any(UserRequestDto.class))).thenReturn(userResponseDto);

        /// Act && Assert\
        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequestDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.username").value( userResponseDto.getUsername()))
                .andExpect(jsonPath("$.email").value(userResponseDto.getEmail()))
                .andExpect(jsonPath("$.address").value(userResponseDto.getAddress()))
                .andExpect(jsonPath("$.phoneNumber").value(userResponseDto.getPhoneNumber()))
                .andExpect(jsonPath("$.role").value(Role.USER.name()))
                .andExpect(jsonPath("$.createdAt").isEmpty())
                .andExpect(jsonPath("$.updatedAt").isEmpty());

    }

    @Test
    @WithMockUser(roles="ADMIN")
    public void testGetUser() throws Exception {
        /// Arrange
        when(userService.getUser(any(Long.class))).thenReturn(userResponseDto);

        mockMvc.perform(get("/api/v1/users/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.username").value( userResponseDto.getUsername()))
                .andExpect(jsonPath("$.email").value(userResponseDto.getEmail()))
                .andExpect(jsonPath("$.address").value(userResponseDto.getAddress()))
                .andExpect(jsonPath("$.phoneNumber").value(userResponseDto.getPhoneNumber()))
                .andExpect(jsonPath("$.role").value(Role.USER.name()))
                .andExpect(jsonPath("$.createdAt").isEmpty())
                .andExpect(jsonPath("$.updatedAt").isEmpty());
    }

    @Test
    @WithMockUser(roles="ADMIN")
        public void testGetAllUsers() throws Exception {
            /// Arrange
        UserResponseDto userResponseDto2 = UserResponseDto.builder()
                .id(2L)
                .email("test2@example.com")
                .username("testuser2")
                .address("3021 S Clark")
                .phoneNumber("1234567890")
                .role(Role.USER)
                .build();
        List<UserResponseDto> users = List.of(userResponseDto, userResponseDto2);
       var userPage = new PageImpl<>(users);

        when(userService.getAllUsers(any(Pageable.class))).thenReturn(userPage);

        /// when and then
        mockMvc.perform(get("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content",hasSize(2)))
                .andExpect(jsonPath("$.content[0].id").value(userResponseDto.getId()))
                .andExpect(jsonPath("$.content[0].username").value(userResponseDto.getUsername()))
                .andExpect(jsonPath("$.content[0].email").value(userResponseDto.getEmail()))
                .andExpect(jsonPath("$.content[0].address").value(userResponseDto.getAddress()))
                .andExpect(jsonPath("$.content[0].phoneNumber").value(userResponseDto.getPhoneNumber()))
                .andExpect(jsonPath("$.content[0].role").value(userResponseDto.getRole().name()))
                .andExpect(jsonPath("$.content[0].createdAt").isEmpty())
                .andExpect(jsonPath("$.content[0].updatedAt").isEmpty())
                .andExpect(jsonPath("$.content[1].id").value(userResponseDto2.getId()))
                .andExpect(jsonPath("$.content[1].username").value(userResponseDto2.getUsername()))
                .andExpect(jsonPath("$.content[1].email").value(userResponseDto2.getEmail()))
                .andExpect(jsonPath("$.content[1].address").value(userResponseDto2.getAddress()))
                .andExpect(jsonPath("$.content[1].phoneNumber").value(userResponseDto2.getPhoneNumber()))
                .andExpect(jsonPath("$.content[1].role").value(userResponseDto2.getRole().name()));
        }


        @Test
        @WithMockUser(roles="ADMIN")
    public void testDeleteUser() throws Exception {

            /// Arrange
            Long userId = 1L;
           doNothing().when(userService).deleteUser(anyLong());

           /// Act && Assert
           mockMvc.perform(delete("/api/v1/users/{userId}",userId)
                   .contentType(MediaType.APPLICATION_JSON))
                   .andDo(print())
                   .andExpect(status().isNoContent());



        }

    @Test

    public void testUpdateUser() throws Exception {
        UserRequestDto updateUser = UserRequestDto.builder()
                .email("test@example.com")
                .password("encodedPassword")
                .username("testuser")
                .address("3021 S Clark")
                .phoneNumber("1234567890")
                .role(Role.USER)
                .build();
        UserResponseDto updateUserResponse = UserResponseDto.builder()
                .id(1L)
                .username(updateUser.getUsername())
                .email(updateUser.getEmail())
                .address(updateUser.getAddress())
                .phoneNumber(updateUser.getPhoneNumber())
                .role(updateUser.getRole())
                .build();

        /// Arrange
            when(userService.updateUser(anyLong(), any(UserRequestDto.class))).thenReturn(updateUserResponse);


            /// Act && Assert
            mockMvc.perform(put("/api/v1/users/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(updateUser)))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(1L))
                    .andExpect(jsonPath("$.username").value( updateUser.getUsername()))
                    .andExpect(jsonPath("$.email").value(updateUser.getEmail()))
                    .andExpect(jsonPath("$.address").value(updateUser.getAddress()))
                    .andExpect(jsonPath("$.phoneNumber").value(updateUser.getPhoneNumber()))
                    .andExpect(jsonPath("$.role").value(updateUser.getRole().name()));

        }

}
