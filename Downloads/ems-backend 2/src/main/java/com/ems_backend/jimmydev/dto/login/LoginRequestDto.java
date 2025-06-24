package com.ems_backend.jimmydev.dto.login;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter @Setter
@RequiredArgsConstructor
public class LoginRequestDto {
    private String firstName;
    private String password;
    private String email;
}
