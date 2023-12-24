package org.example.lesson5.controller;

import lombok.RequiredArgsConstructor;
import org.example.lesson5.dto.BookListDto;
import org.example.lesson5.service.CategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/{categoryName}/books")
    public BookListDto getBooksByCategory(@PathVariable String categoryName) {
        return categoryService.getBooksByCategory(categoryName);
    }
}
