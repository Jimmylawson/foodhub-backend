package com.jimmydev.blog_api.dtos.userdto;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PostSummaryDto {
    private String title;
    private String content;

}
