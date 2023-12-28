package org.example.lesson5.service;

import lombok.RequiredArgsConstructor;
import org.example.lesson5.config.properties.AppCacheProperties;
import org.example.lesson5.dto.BookDto;
import org.example.lesson5.dto.BookListDto;
import org.example.lesson5.exception.EntityNotFoundException;
import org.example.lesson5.model.Category;
import org.example.lesson5.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;

    @Cacheable(AppCacheProperties.CacheNames.BOOKS_BY_CATEGORY)
    public BookListDto getBooksByCategory(String categoryName) {
        Category category = categoryRepository.findByNameIgnoreCase(categoryName)
                                              .orElseThrow(EntityNotFoundException::new);

        return new BookListDto(category
                                       .getBooks()
                                       .stream()
                                       .map(book -> modelMapper.map(book, BookDto.class))
                                       .toList());
    }
}
