package com.jimmydev.blog_api.dtos.postdtos;


import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@Getter @Setter @Builder
@NoArgsConstructor
public class PostRequestDto {
    @NotBlank(message = "Title is required")
    private String title;
    @NotBlank(message = "Content is required")
    private String content;
    @NotBlank(message = "Author is required")
    private Long authorId;
}
