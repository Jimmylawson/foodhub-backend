package com.food_delivery.zomato_backend.Service;

import com.food_delivery.zomato_backend.dtos.UserDtos.UserRequestDto;
import com.food_delivery.zomato_backend.dtos.UserDtos.UserResponseDto;
import com.food_delivery.zomato_backend.entity.User;
import com.food_delivery.zomato_backend.enumTypes.Role;
import com.food_delivery.zomato_backend.exceptions.users.DuplicateUserException;
import com.food_delivery.zomato_backend.exceptions.users.UserNotFoundException;
import com.food_delivery.zomato_backend.mapper.userMappers.UserMapper;
import com.food_delivery.zomato_backend.repository.UserRepository;
import com.food_delivery.zomato_backend.service.UserService.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
   @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserMapper userMapper;
    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private UserRequestDto requestDto;

    @BeforeEach
    public void setUp(){
        user = User.builder()
                .id(1L)
                .email("test@example.com")
                .username("testuser")
                .password("encodedPassword")
                .address("123 Test St")
                .phoneNumber("1234567890")
                .role(Role.USER)
                .build();

        /// 1. Prepare test data
         requestDto = UserRequestDto.builder()
                .username("testuser")
                .email("test@example.com")
                .password("encodedPassword")
                .address("123 Test St")
                .phoneNumber("1234567890")
                .role(Role.USER)
                .build();
    }



    @Test
    void saveUserTest(){


        /// Set up mock behavoir
        when(userRepository.existsByEmail(requestDto.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(requestDto.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userMapper.toEntity(requestDto)).thenReturn(user);
        when(userMapper.toUserResponseDto(user)).thenReturn(
                UserResponseDto.builder()
                        .id(user.getId())
                        .email(user.getEmail())
                        .username(user.getUsername())
                        .build()
        );



        var response = userService.createUser(requestDto);

        /// 4 Vewry the result
        assertNotNull(response);
        assertEquals(user.getEmail(), response.getEmail());
        assertEquals(user.getUsername(), response.getUsername());

        /// Verify interactions
        verify(userRepository).existsByEmail(requestDto.getEmail());
        verify(passwordEncoder).encode(requestDto.getPassword());
        verify(userMapper).toEntity(requestDto);
        verify(userMapper).toUserResponseDto(user);
        verify(userRepository).save(any(User.class));
    }

    @Test
    public void saveUserDuplicateEmailTest(){
        when(userRepository.existsByEmail(requestDto.getEmail())).thenReturn(true);
        assertThrows(DuplicateUserException.class, () -> {
            userService.createUser(requestDto);
        });

        verify(userRepository).existsByEmail(requestDto.getEmail());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void getUserTest(){

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.toUserResponseDto(user)).thenReturn(
                UserResponseDto.builder()
                        .id(user.getId())
                        .email(user.getEmail())
                        .username(user.getUsername())
                        .build()
        );

        var response = userService.getUser(1L);
        /// Asserts
        assertNotNull(response);
        assertEquals(user.getId(), response.getId());
        assertEquals(user.getEmail(), response.getEmail());
        assertEquals(user.getUsername(), response.getUsername());

        verify(userRepository).findById(1L);
    }

    @Test
    public void getUserNotFoundTest(){
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> {
            userService.getUser(1L);
        });
        verify(userRepository).findById(1L);
    }

    @Test
    public void getAllUsersTest(){
        User user2 = User.builder()
                .id(2L)
                .email("test2@example.com")
                .username("testuser2")
                .password("password")
                .address("3021 S Clark")
                .phoneNumber("1234567890")
                .role(Role.USER)
                .build();


        /// Prepare expected response
        UserResponseDto userResponseDto = UserResponseDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .build();

        UserResponseDto userResponseDto2 = UserResponseDto.builder()
                .id(user2.getId())
                .email(user2.getEmail())
                .username(user2.getUsername())
                .build();

        var users = List.of(user,user2);
        var dtos = List.of(userResponseDto,userResponseDto2);

        Pageable pageable = Pageable.unpaged();
        var userPage = new PageImpl<> (users, pageable, users.size());
        ///  Stub repository and mapper
        when(userRepository.findAll(pageable)).thenReturn(userPage);
        when(userMapper.toUserResponseDto(user)).thenReturn(userResponseDto);
        when(userMapper.toUserResponseDto(user2)).thenReturn(userResponseDto2);

        /// Execute
        var result = userService.getAllUsers(pageable);

        /// assert
        assertEquals(2, result.getContent().size());
        assertTrue(result.getContent().containsAll(dtos)); // .()

        /// verify
        verify(userRepository).findAll(pageable);
        verify(userMapper).toUserResponseDto(user);
        verify(userMapper).toUserResponseDto(user2);


    }

    @Test
    public void deleteUserTest(){
        /// Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));


          /// Act
        userService.deleteUser(1L);

        ///Assert
        verify(userRepository).findById(1L);
        verify(userRepository).deleteById(1L);
    }
    @Test
    public void deleteUserThrowsWhenNotFound() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.deleteUser(99L));

        verify(userRepository).findById(99L);
        verify(userRepository, never()).delete(any());
    }
}