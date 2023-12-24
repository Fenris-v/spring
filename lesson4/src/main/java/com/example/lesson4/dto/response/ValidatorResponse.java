package com.example.lesson4.dto.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
public class ValidatorResponse {
    private final LocalDateTime timestamp = LocalDateTime.now();
    private final Integer status = HttpStatus.UNPROCESSABLE_ENTITY.value();
    private final Map<String, String> messages;

    public ValidatorResponse(Map<String, String> messages) {
        this.messages = messages;
    }
}
