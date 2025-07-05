package com.jimmydev.blog_api.mapper;

import com.jimmydev.blog_api.dtos.postdtos.AuthorDto;
import com.jimmydev.blog_api.entity.User;
import org.springframework.stereotype.Component;


@Component
public class AuthorMapper {

    public AuthorDto toAuthorDto(User user){
        return AuthorDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .build();
    }

    public User toAuthor(AuthorDto authorDto){
        return User.builder()
                .id(authorDto.getId())
                .username(authorDto.getUsername())
                .build();
    }

    public User toAuthor(Long id){
        return User.builder()
                .id(id)
                .build();
    }
}
