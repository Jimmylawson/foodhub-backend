package com.food_delivery.zomato_backend.service.UserService;


import com.food_delivery.zomato_backend.dtos.UserDtos.UserRequestDto;
import com.food_delivery.zomato_backend.dtos.UserDtos.UserResponseDto;
;

import java.util.List;

public interface UserServiceInterface {
    UserResponseDto createUser(UserRequestDto userRequestDto);
    UserResponseDto getUser(Long userId);
    List<UserResponseDto> getAllUsers();
    void deleteUser(Long userId);
    UserResponseDto updateUser(Long userId, UserRequestDto userRequestDto);


}
