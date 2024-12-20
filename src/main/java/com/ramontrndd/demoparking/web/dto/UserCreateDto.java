package com.ramontrndd.demoparking.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString

public class UserCreateDto {

    @NotBlank
    @Email(message = "Email should be valid", regexp = "^[a-z0-9.+-]+@[a-z0-9.-]+\\.[a-z]{2,}$")
    private String name;
    @NotBlank
    @Size(min = 6, max=10, message = "Password should be at least 6 characters long")
    private String password;
}
