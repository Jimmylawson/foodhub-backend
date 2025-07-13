package com.food_delivery.zomato_backend.mapper.userMappers;


import com.food_delivery.zomato_backend.dtos.UserDtos.UserBasicDto;
import com.food_delivery.zomato_backend.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserBasicMapper {
    UserBasicDto toUserBasicDto(User user);
}
