package com.example.lesson4.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewsDto {
    private Long id;
    private String name;
    private String text;
    private UserDto user;
    private CategoryDto category;
    private LocalDateTime createdAt;
}
