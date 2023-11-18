package com.example.lesson4.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewsFilter {
    private Long categoryId;
    private Long userId;
    private int page;
    private int perPage = 10;
}
