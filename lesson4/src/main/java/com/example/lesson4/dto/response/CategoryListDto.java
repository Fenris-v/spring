package com.example.lesson4.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CategoryListDto {
    private final List<CategoryDto> data;
}
