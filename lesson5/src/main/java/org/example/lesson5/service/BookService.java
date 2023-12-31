package org.example.lesson5.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.example.lesson5.config.properties.AppCacheProperties;
import org.example.lesson5.dto.BookDto;
import org.example.lesson5.dto.BookListDto;
import org.example.lesson5.dto.BookRequest;
import org.example.lesson5.exception.EntityNotFoundException;
import org.example.lesson5.model.Book;
import org.example.lesson5.model.Category;
import org.example.lesson5.repository.BookRepository;
import org.example.lesson5.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
    private final CategoryRepository categoryRepository;
    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;

    @PostConstruct
    private void configureMappings() {
        modelMapper.addMappings(new BookPropertyMap());
    }

    @Cacheable(AppCacheProperties.CacheNames.SEARCH_BOOK)
    public BookListDto search(String name, String author) {
        List<Book> bookList = bookRepository.findByNameAndAuthor(name, author);
        List<BookDto> bookDtoList = bookList.stream().map(book -> modelMapper.map(book, BookDto.class)).toList();

        return new BookListDto(bookDtoList);
    }

    @Cacheable(value = AppCacheProperties.CacheNames.BOOK_BY_ID, key = "#id")
    public BookDto getById(Long id) {
        Book book = bookRepository.findFirstById(id).orElseThrow(EntityNotFoundException::new);
        return modelMapper.map(book, BookDto.class);
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = AppCacheProperties.CacheNames.BOOKS_BY_CATEGORY, allEntries = true),
            @CacheEvict(value = AppCacheProperties.CacheNames.SEARCH_BOOK, allEntries = true)
    })
    public @NonNull BookDto create(@NonNull BookRequest bookRequest) {
        Category category = categoryRepository.findById(bookRequest.getCategoryId())
                                              .orElseThrow(EntityNotFoundException::new);

        Book book = modelMapper.map(bookRequest, Book.class);
        book.setCategory(category);
        book = bookRepository.save(book);

        return modelMapper.map(book, BookDto.class);
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = AppCacheProperties.CacheNames.BOOKS_BY_CATEGORY, allEntries = true),
            @CacheEvict(value = AppCacheProperties.CacheNames.SEARCH_BOOK, allEntries = true),
            @CacheEvict(value = AppCacheProperties.CacheNames.BOOK_BY_ID, key = "#id")
    })
    public BookDto update(Long id, @NonNull BookRequest bookRequest) {
        Book book = bookRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        Category category = categoryRepository.findById(bookRequest.getCategoryId())
                                              .orElseThrow(EntityNotFoundException::new);
        modelMapper.map(bookRequest, book);
        book.setCategory(category);
        book = bookRepository.save(book);

        return modelMapper.map(book, BookDto.class);
    }

    @Caching(evict = {
            @CacheEvict(value = AppCacheProperties.CacheNames.BOOKS_BY_CATEGORY, allEntries = true),
            @CacheEvict(value = AppCacheProperties.CacheNames.SEARCH_BOOK, allEntries = true),
            @CacheEvict(value = AppCacheProperties.CacheNames.BOOK_BY_ID, key = "#id")
    })
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }
}

class BookPropertyMap extends PropertyMap<BookRequest, Book> {
    @Override
    protected void configure() {
        skip(destination.getId());
        skip(destination.getCategory());
    }
}
