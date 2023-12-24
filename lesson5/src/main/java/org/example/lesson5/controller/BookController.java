package org.example.lesson5.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.lesson5.dto.BookDto;
import org.example.lesson5.dto.BookListDto;
import org.example.lesson5.dto.BookRequest;
import org.example.lesson5.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    @GetMapping("/search")
    public BookListDto search(@RequestParam(defaultValue = "") String name,
                              @RequestParam(defaultValue = "") String author) {
        return bookService.search(name, author);
    }

    @GetMapping("/{id}")
    public BookDto getById(@PathVariable Long id) {
        return bookService.getById(id);
    }

    @PostMapping
    public BookDto create(@RequestBody @Valid BookRequest bookRequest) {
        return bookService.create(bookRequest);
    }

    @PutMapping("/{id}")
    public BookDto update(@PathVariable Long id, @RequestBody @Valid BookRequest bookRequest) {
        return bookService.update(id, bookRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        bookService.deleteById(id);
        return ResponseEntity.accepted().build();
    }
}
