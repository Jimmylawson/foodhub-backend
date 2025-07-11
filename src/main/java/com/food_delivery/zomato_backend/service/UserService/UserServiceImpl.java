package com.food_delivery.zomato_backend.service.UserService;

import com.food_delivery.zomato_backend.dtos.UserDtos.UserRequestDto;
import com.food_delivery.zomato_backend.dtos.UserDtos.UserResponseDto;
import com.food_delivery.zomato_backend.mapper.UserMapper;
import com.food_delivery.zomato_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserServiceInterface {
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    @Override
    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        return null;
    }

    @Override
    public UserResponseDto getUser(Long userId) {
        return null;
    }

    @Override
    public List<UserResponseDto> getAllUsers() {
        return List.of();
    }

    @Override
    public void deleteUser(Long userId) {

    }

    @Override
    public UserResponseDto updateUser(Long userId, UserRequestDto userRequestDto) {
        return null;
    }
}
