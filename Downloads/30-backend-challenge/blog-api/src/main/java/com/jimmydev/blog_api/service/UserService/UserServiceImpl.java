package com.jimmydev.blog_api.service.UserService;

import com.jimmydev.blog_api.dtos.userdto.UserRequestDto;
import com.jimmydev.blog_api.dtos.userdto.UserResponseDto;
import com.jimmydev.blog_api.exceptions.UserNotFoundException;
import com.jimmydev.blog_api.mapper.UserMapper;
import com.jimmydev.blog_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserResponseDto findUserById(Long id){

        return userMapper.toUserResponseDto(userRepository.findById(id)
                .orElseThrow(()-> new UserNotFoundException("User not found")));
    }
    @Override
    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        return userMapper.toUserResponseDto(userRepository.save(userMapper.toEntity(userRequestDto)));
    }

    @Override
    public UserResponseDto getUser(Long id) {
        return  findUserById(id);
    }

    @Override
    public UserResponseDto updateUser(Long id, UserRequestDto userRequestDto) {
        var user = userMapper.toEntity(userRequestDto);
        user.setId(id);
        return userMapper.toUserResponseDto(userRepository.save(user));
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
    @Override
    public Page<UserResponseDto> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(userMapper::toUserResponseDto);

    }

    @Override
    public Long getUserCount() {
        return userRepository.count();
    }


}
