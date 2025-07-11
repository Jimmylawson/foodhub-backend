package com.food_delivery.zomato_backend.dtos.UserDtos;


import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserBasicDto {
    private Long id;
    private String name;
    private String email;
}
