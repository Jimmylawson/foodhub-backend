package com.food_delivery.zomato_backend.mapper;


import com.food_delivery.zomato_backend.dtos.UserDtos.UserRequestDto;
import com.food_delivery.zomato_backend.dtos.UserDtos.UserResponseDto;
import com.food_delivery.zomato_backend.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

     UserResponseDto toUserResponseDto(User user);
     User toEntity(UserRequestDto userRequestDto);
}
