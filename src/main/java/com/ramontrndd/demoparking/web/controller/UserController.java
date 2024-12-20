package com.ramontrndd.demoparking.web.controller;


import com.ramontrndd.demoparking.entity.User;
import com.ramontrndd.demoparking.service.UserService;

import com.ramontrndd.demoparking.web.dto.UserCreateDto;
import com.ramontrndd.demoparking.web.dto.UserPasswordDto;
import com.ramontrndd.demoparking.web.dto.UserResponseDto;
import com.ramontrndd.demoparking.web.dto.mapper.UserMapper;
import com.ramontrndd.demoparking.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Usuários", description = "Endpoints para gerenciamento de usuários")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/users")
public class UserController {


    private final UserService userService;



    @Operation(summary = "Busca um usuário por ID", description = "Busca um usuário com base no ID informado",
            responses = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))})
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getById(@PathVariable Long id) {
        User user = userService.findById(id);
        return ResponseEntity.ok(UserMapper.toDto(user));
    }


    @Operation(summary = "Busca todos os usuários", description = "Busca todos os usuários cadastrados",
            responses = {
            @ApiResponse(responseCode = "200", description = "Usuários encontrados com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))),

            @ApiResponse(responseCode = "404", description = "Nenhum usuário encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))})
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAll() {
        List<User> users = userService.findAll();
        return ResponseEntity.ok(UserMapper.toDtoList(users));
    }
    @Operation(summary = "Cria um novo usuário", description = "Cria um novo usuário com base nos dados informados",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))),
                    @ApiResponse(responseCode = "409", description = "O Usuário já existe em nosso banco de dados", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Recurso não processado por dados de entrada invalidos", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))})

    @PostMapping
    public ResponseEntity<UserResponseDto> create(@Valid @RequestBody UserCreateDto createDto) {
        User user = userService.save(UserMapper.toUser(createDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toDto(user));
    }


    @Operation(summary = "Atualiza a senha de um usuário", description = "Atualiza a senha de um usuário com base no ID informado",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Senha atualizada com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))),
                    @ApiResponse(responseCode = "400", description = "Senha não confere", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404", description = "Recurso não processado por dados de entrada invalidos", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Campos inválidos", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))})
    @PatchMapping("/{id}")
    public ResponseEntity<Void> updatePassword(@PathVariable Long id, @Valid @RequestBody UserPasswordDto dto) {
        User user = userService.editPassword(id, dto.getCurrentPassword(), dto.getNewPassword(), dto.getConfirmPassword());
        return ResponseEntity.noContent().build();
    }
}
