package com.example.lesson4.dto.response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ExceptionDto {
    private final LocalDateTime timestamp = LocalDateTime.now();
    private final String message;
    private final Integer status;

    public ExceptionDto(String message, Integer status) {
        this.message = message;
        this.status = status;
    }
}
