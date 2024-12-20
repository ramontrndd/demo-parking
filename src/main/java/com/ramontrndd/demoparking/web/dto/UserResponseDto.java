package com.ramontrndd.demoparking.web.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class UserResponseDto {

    private Long id;
    private String name;
    private String role;
}
