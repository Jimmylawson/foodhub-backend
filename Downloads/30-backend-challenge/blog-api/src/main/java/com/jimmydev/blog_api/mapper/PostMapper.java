package com.jimmydev.blog_api.mapper;

import com.jimmydev.blog_api.dtos.postdtos.PostRequestDto;
import com.jimmydev.blog_api.dtos.postdtos.PostResponseDto;
import com.jimmydev.blog_api.dtos.userdto.UserResponseDto;
import com.jimmydev.blog_api.entity.Post;
import com.jimmydev.blog_api.entity.User;
import com.jimmydev.blog_api.repository.UserRepository;
import com.jimmydev.blog_api.service.UserService.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class PostMapper {
    private final AuthorMapper authorMapper;
    private final UserService userService;

    public PostResponseDto toPostResponseDto(Post post){

        return PostResponseDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .author(authorMapper.toAuthorDto(post.getAuthor()))
                .build();
    }
    public Post toEntity(PostRequestDto postRequestDto) {
        UserResponseDto userResponse = userService.getUser(postRequestDto.getAuthorId());
        User author = User.builder()
                .id(userResponse.getId())
                .username(userResponse.getUsername())
                .email(userResponse.getEmail())
                .post(new ArrayList<>()) // Initialize the post list
                .build();
                
        Post post = Post.builder()
                .title(postRequestDto.getTitle())
                .content(postRequestDto.getContent())
                .author(author)
                .build();
                
        // Add the post to the user's list of posts using the helper method
        author.addPost(post);
        
        return post;
    }
}
