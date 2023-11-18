package com.example.lesson4.controller;

import com.example.lesson4.dto.request.NewsFilter;
import com.example.lesson4.dto.request.NewsRequest;
import com.example.lesson4.dto.response.NewsDto;
import com.example.lesson4.service.NewsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/api/v1")
public class NewsController {
    private final NewsService newsService;

    @GetMapping("/news")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Ok")})
    @Operation(summary = "Фильтр новостей", description = "Фильтр новостей")
    public Page<NewsDto> getFilteredNews(NewsFilter newsFilter) {
        return newsService.getFilteredNews(newsFilter);
    }

    @GetMapping("/category/{categoryId}/news")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Ok")})
    @Operation(summary = "Список новостей", description = "Список новостей")
    public Page<NewsDto> getNews(@PathVariable Long categoryId,
                                 @RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int perPage) {
        return newsService.getNews(categoryId, page, perPage);
    }

    @GetMapping("/news/{newsId}")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Ok",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = NewsDto.class))}),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content())})
    @Operation(summary = "Получить новость", description = "Получить новость")
    public NewsDto getNewsById(@PathVariable Long newsId) {
        return newsService.getNewsById(newsId);
    }

    @PostMapping("/news")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Ok",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = NewsDto.class))}),
            @ApiResponse(responseCode = "422", description = "Ошибка валидации", content = @Content())})
    @Operation(summary = "Создать новость", description = "Создать новость")
    public NewsDto createNews(@RequestHeader(HttpHeaders.AUTHORIZATION) Long userId,
                              @RequestBody @Valid NewsRequest newsRequest) {
        return newsService.createNews(userId, newsRequest);
    }

    @PutMapping("/news/{newsId}")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Ok",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = NewsDto.class))}),
            @ApiResponse(responseCode = "401", description = "Unathorized", content = @Content()),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content())})
    @Operation(summary = "Изменить новость", description = "Изменить новость")
    public NewsDto editNews(@RequestHeader(HttpHeaders.AUTHORIZATION) Long userId,
                            @PathVariable Long newsId,
                            @RequestBody @Valid NewsRequest newsRequest) {
        return newsService.editNews(userId, newsId, newsRequest);
    }

    @DeleteMapping("/news/{newsId}")
    @ApiResponses(value = {@ApiResponse(responseCode = "202", description = "Accepted", content = @Content()),
            @ApiResponse(responseCode = "401", description = "Unathorized", content = @Content()),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content())})
    @Operation(summary = "Удалить новость", description = "Удалить новость")
    public ResponseEntity<Object> deleteNews(@RequestHeader(HttpHeaders.AUTHORIZATION) Long userId,
                                             @PathVariable Long newsId) {
        newsService.deleteNews(userId, newsId);
        return ResponseEntity.accepted().build();
    }
}
