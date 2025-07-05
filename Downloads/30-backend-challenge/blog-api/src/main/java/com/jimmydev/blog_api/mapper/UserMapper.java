package com.jimmydev.blog_api.mapper;

import com.jimmydev.blog_api.dtos.userdto.PostSummaryDto;
import com.jimmydev.blog_api.dtos.userdto.UserRequestDto;
import com.jimmydev.blog_api.dtos.userdto.UserResponseDto;
import com.jimmydev.blog_api.entity.Post;
import com.jimmydev.blog_api.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {
    public UserResponseDto toUserResponseDto(User user){
        var postDtos = user.getPost().stream()
                .map(post -> PostSummaryDto.builder()
                        .title(post.getTitle())
                        .content(post.getContent())
                        .build())
                .collect(Collectors.toList());
        return UserResponseDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .posts(postDtos)
                .build();

    }
    public User toEntity(UserRequestDto dto){
        User user = User.builder()
                .username(dto.getUsername())
                .email(dto.getEmail())
                .build();

        if (dto.getPosts() != null) {
            List<Post> posts = dto.getPosts().stream()
                    .map(postDto -> {
                        Post post = new Post();
                        post.setTitle(postDto.getTitle());
                        post.setContent(postDto.getContent());
                        post.setAuthor(user); // very important
                        return post;
                    }).collect(Collectors.toList());

            user.setPost(posts);
        }

        return user;
    }
}
