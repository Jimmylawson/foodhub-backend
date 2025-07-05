package com.jimmydev.blog_api.dtos.userdto;

import com.jimmydev.blog_api.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Builder
@Setter
@AllArgsConstructor
@Getter
public class UserResponseDto {
    private Long id;
    private String  username;
    private String email;
    private List<PostSummaryDto> posts;
}
