package com.ramontrndd.demoparking.web.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString


public class UserResponseDto {

    private long id;
    private String name;
    private String role;
}
