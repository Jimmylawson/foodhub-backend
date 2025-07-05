package com.jimmydev.blog_api.dtos.postdtos;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AuthorDto {
    private Long id;
    private String username;
}
