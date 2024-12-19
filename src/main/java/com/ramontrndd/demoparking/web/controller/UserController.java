package com.ramontrndd.demoparking.web.controller;


import com.ramontrndd.demoparking.entity.User;
import com.ramontrndd.demoparking.service.UserService;

import com.ramontrndd.demoparking.web.dto.UserCreateDto;
import com.ramontrndd.demoparking.web.dto.UserPasswordDto;
import com.ramontrndd.demoparking.web.dto.UserResponseDto;
import com.ramontrndd.demoparking.web.dto.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor

@RestController
@RequestMapping("api/v1/users")
public class UserController {


    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDto> create(@RequestBody UserCreateDto createDto) {
        User user = userService.save(UserMapper.toUser(createDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toDto(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getById(@PathVariable Long id) {
        User user = userService.findById(id);
        return ResponseEntity.ok(UserMapper.toDto(user));
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAll() {
        List<User> users = userService.findAll();
        return ResponseEntity.ok(UserMapper.toDtoList(users));
    }
    @PatchMapping("/{id}")
    public ResponseEntity<Void> updatePassword(@PathVariable Long id, @RequestBody UserPasswordDto dto) {
        User user = userService.editPassword(id, dto.getCurrentPassword(), dto.getNewPassword(), dto.getConfirmPassword());
        return ResponseEntity.noContent().build();
    }
}
