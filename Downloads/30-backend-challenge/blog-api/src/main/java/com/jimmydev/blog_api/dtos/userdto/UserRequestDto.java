package com.jimmydev.blog_api.dtos.userdto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Builder
@Setter @Getter
@AllArgsConstructor
public class UserRequestDto {
    @NotBlank(message = "username is required")
    private String username;
    @NotBlank(message = "email is required")
    private String email;
    @NotEmpty(message = "post list cannot be null")
    private List<PostSummaryDto> posts;
}
