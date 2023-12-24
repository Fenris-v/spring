package com.example.lesson4.controller;

import com.example.lesson4.dto.request.CommentRequest;
import com.example.lesson4.dto.response.CommentDto;
import com.example.lesson4.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Tag(name = "Комментарий")
@RequestMapping("/api/v1/news/{newsId}/comment")
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Ok",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CommentDto.class))}),
            @ApiResponse(responseCode = "422", description = "Ошибка валидации", content = @Content())})
    @Operation(summary = "Создать комментарий", description = "Создать комментарий")
    public CommentDto createNews(@RequestHeader(HttpHeaders.AUTHORIZATION) Long userId,
                                 @PathVariable Long newsId,
                                 @RequestBody @Valid CommentRequest commentRequest) {
        return commentService.createComment(userId, newsId, commentRequest);
    }

    @PutMapping("/{commentId}")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Ok",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CommentDto.class))}),
            @ApiResponse(responseCode = "401", description = "Unathorized", content = @Content()),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content())})
    @Operation(summary = "Изменить комментарий", description = "Изменить комментарий")
    public CommentDto editNews(@RequestHeader(HttpHeaders.AUTHORIZATION) Long userId,
                               @PathVariable Long newsId,
                               @PathVariable UUID commentId,
                               @RequestBody @Valid CommentRequest commentRequest) {
        return commentService.editComment(userId, commentId, commentRequest);
    }

    @DeleteMapping("/{commentId}")
    @ApiResponses(value = {@ApiResponse(responseCode = "202", description = "Accepted", content = @Content()),
            @ApiResponse(responseCode = "401", description = "Unathorized", content = @Content()),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content())})
    @Operation(summary = "Удалить пользователя", description = "Удалить пользователя")
    public ResponseEntity<Object> deleteComment(@RequestHeader(HttpHeaders.AUTHORIZATION) Long userId,
                                                @PathVariable Long newsId,
                                                @PathVariable UUID commentId) {
        commentService.deleteComment(userId, commentId);
        return ResponseEntity.accepted().build();
    }
}
