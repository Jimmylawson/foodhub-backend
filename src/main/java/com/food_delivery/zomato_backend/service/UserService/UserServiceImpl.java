package com.food_delivery.zomato_backend.service.UserService;

import com.food_delivery.zomato_backend.dtos.UserDtos.UserRequestDto;
import com.food_delivery.zomato_backend.dtos.UserDtos.UserResponseDto;
import com.food_delivery.zomato_backend.entity.User;
import com.food_delivery.zomato_backend.exceptions.users.DuplicateUserException;
import com.food_delivery.zomato_backend.exceptions.users.UserNotFoundException;
import com.food_delivery.zomato_backend.mapper.UserMapper;
import com.food_delivery.zomato_backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserServiceInterface {
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    @Override
    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        if(userRepository.existsByEmail(userRequestDto.getEmail()))
            throw new DuplicateUserException("User with email " + userRequestDto.getEmail() + " already exists");

       var user = userMapper.toEntity(userRequestDto);
        /// Set any default values
       user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        /// Save the user first
       var savedUser = userRepository.save(user);

       /// Map back to the UserResponseDto
       return userMapper.toUserResponseDto(savedUser);

    }

    @Override
    public UserResponseDto getUser(Long userId) {
        return userRepository.findById(userId)
                .map(userMapper::toUserResponseDto)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    @Override
    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toUserResponseDto)
                .toList();
    }

    @Override
    @Transactional
    public void deleteUser(Long userId) {
        /// If the user does not exist throw an exception
        var user  = getUserOrThrow(userId);

        userRepository.deleteById(user.getId());

    }

    @Override
    @Transactional
    public UserResponseDto updateUser(Long userId, UserRequestDto userRequestDto) {
        /// Fetch existing user or throw an exception
        var user = getUserOrThrow(userId);

        /// Check if  email already exists and if new email alreadhy exist
        if(userRepository.existsByEmail(userRequestDto.getEmail())
        && !user.getEmail().equals(userRequestDto.getEmail())
        )
            throw new DuplicateUserException("User with email " + userRequestDto.getEmail() + " already exists");

         user.builder()
                 .email(userRequestDto.getEmail())
                 .address(userRequestDto.getAddress())
                 .phoneNumber(userRequestDto.getPhoneNumber())
                 .role(userRequestDto.getRole())
                 .build();

         /// Update the user timestamps
        userRepository.save(user);
        /// Map and return the updated user
        return userMapper.toUserResponseDto(user);
    }

    private User getUserOrThrow(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }
}
