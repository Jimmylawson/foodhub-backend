package com.jimmydev.blog_api.service.UserService;

import com.jimmydev.blog_api.dtos.userdto.UserRequestDto;
import com.jimmydev.blog_api.dtos.userdto.UserResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    public UserResponseDto createUser(UserRequestDto userRequestDto);
    public UserResponseDto getUser(Long id);
    public UserResponseDto updateUser(Long id, UserRequestDto userRequestDto);
    public void deleteUser(Long id);
    Page<UserResponseDto> getAllUsers(Pageable pageable);
    public Long getUserCount();

}
