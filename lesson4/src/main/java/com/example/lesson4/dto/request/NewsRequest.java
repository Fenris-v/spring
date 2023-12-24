package com.example.lesson4.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NewsRequest {
    @NotNull
    @Size(min = 2, max = 255)
    private String name;

    @NotNull
    @Size(min = 20)
    private String text;

    private Long categoryId;
}
