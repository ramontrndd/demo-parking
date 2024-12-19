package com.ramontrndd.demoparking.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString

public class UserCreateDto {

    @NotBlank
    @Email(message = "Email should be valid", regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    private String name;
    @NotBlank
    @Size(min = 6, max=6, message = "Password should be at least 6 characters long")
    private String password;
}
