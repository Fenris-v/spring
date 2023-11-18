package com.example.lesson4.controller;

import com.example.lesson4.dto.request.CategoryRequest;
import com.example.lesson4.dto.response.CategoryDto;
import com.example.lesson4.dto.response.CategoryListDto;
import com.example.lesson4.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Категория")
@RequestMapping("/api/v1/category")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Ok")})
    @Operation(summary = "Список категорий", description = "Список категорий")
    public CategoryListDto getCategories() {
        return categoryService.getCategories();
    }

    @GetMapping("/{categoryId}")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Ok",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CategoryDto.class))}),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content())})
    @Operation(summary = "Получить категорию", description = "Получить категорию")
    public CategoryDto getCategoryById(@PathVariable Long categoryId) {
        return categoryService.getCategoryById(categoryId);
    }

    @PostMapping
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Ok",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CategoryDto.class))}),
            @ApiResponse(responseCode = "422", description = "Ошибка валидации", content = @Content())})
    @Operation(summary = "Создать категорию", description = "Создать категорию")
    public CategoryDto createCategory(@RequestBody @Valid CategoryRequest categoryRequest) {
        return categoryService.createCategory(categoryRequest);
    }

    @PutMapping("/{categoryId}")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Ok",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CategoryDto.class))}),
            @ApiResponse(responseCode = "401", description = "Unathorized", content = @Content()),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content())})
    @Operation(summary = "Изменить категорию", description = "Изменить категорию")
    public CategoryDto editCategory(@PathVariable Long categoryId,
                                    @RequestBody @Valid CategoryRequest categoryRequest) {
        return categoryService.editCategory(categoryId, categoryRequest);
    }

    @DeleteMapping("/{categoryId}")
    @ApiResponses(value = {@ApiResponse(responseCode = "202", description = "Accepted", content = @Content()),
            @ApiResponse(responseCode = "401", description = "Unathorized", content = @Content()),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content())})
    @Operation(summary = "Удалить категорию", description = "Удалить категорию")
    public ResponseEntity<Object> deleteCategory(@PathVariable Long categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.accepted().build();
    }
}
