package com.example.lesson4.controller;

import com.example.lesson4.Authorized;
import com.example.lesson4.dto.request.UserRequest;
import com.example.lesson4.dto.response.UserDto;
import com.example.lesson4.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Пользователь")
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;

    @GetMapping
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Ok")})
    @Operation(summary = "Список пользователей", description = "Список пользователей")
    public Page<UserDto> getUsers(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int perPage) {
        return userService.getUsers(page, perPage);
    }

    @GetMapping("/{userId}")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Ok",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))}),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content())})
    @Operation(summary = "Получить пользователя", description = "Получить пользователя")
    public UserDto getUserById(@PathVariable Long userId) {
        return userService.getUserById(userId);
    }

    @PostMapping
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Ok",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))}),
            @ApiResponse(responseCode = "422", description = "Ошибка валидации", content = @Content())})
    @Operation(summary = "Создать пользователя", description = "Создать пользователя")
    public UserDto createUser(@RequestBody @Valid UserRequest userRequest) {
        return userService.createUser(userRequest);
    }

    @PutMapping
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Ok",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))}),
            @ApiResponse(responseCode = "401", description = "Unathorized", content = @Content()),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content())})
    @Operation(summary = "Изменить пользователя", description = "Изменить пользователя")
    public UserDto editUser(@RequestHeader(HttpHeaders.AUTHORIZATION) Long userId,
                            @RequestBody @Valid UserRequest userRequest) {
        return userService.editUser(userId, userRequest);
    }

    @Authorized
    @DeleteMapping
    @ApiResponses(value = {@ApiResponse(responseCode = "202", description = "Accepted", content = @Content()),
            @ApiResponse(responseCode = "401", description = "Unathorized", content = @Content()),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content())})
    @Operation(summary = "Удалить пользователя", description = "Удалить пользователя")
    public ResponseEntity<Object> deleteUser(@NonNull HttpServletRequest request) {
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        userService.deleteUser(Long.parseLong(authorization));

        return ResponseEntity.accepted().build();
    }
}
