package com.ems_backend.jimmydev.dto;

import com.ems_backend.jimmydev.model.Roles;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class EmployeeRequestDto {
    @NotBlank
    @Schema(description = "Employee first name", example="John")
    private String firstName;
   @NotBlank
   @Schema(description = "Employee last name", example="Doe")
    private String lastName;
    @NotBlank
    @Schema(description = "Employee password", example="password")
    private String password;

    @NotBlank
    @Email
    @Schema(description = "Employee email ", example="john.doe@company.com")
    private String email;
    @NotEmpty
    @Schema(description = "Employee roles", example="[\"USER\", \"ADMIN\"]")
    private Set<Roles> roles;
}
