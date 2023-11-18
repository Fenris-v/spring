package com.example.lesson4.service;

import com.example.lesson4.dto.request.CategoryRequest;
import com.example.lesson4.dto.response.CategoryDto;
import com.example.lesson4.dto.response.CategoryListDto;
import com.example.lesson4.exception.EntityNotFoundException;
import com.example.lesson4.model.Category;
import com.example.lesson4.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;

    public CategoryListDto getCategories() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryDto> categoryDtoList =
                categories.stream().map((element) -> modelMapper.map(element, CategoryDto.class)).toList();

        return new CategoryListDto(categoryDtoList);
    }

    public CategoryDto getCategoryById(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(EntityNotFoundException::new);
        return modelMapper.map(category, CategoryDto.class);
    }

    @Transactional
    public CategoryDto createCategory(CategoryRequest categoryRequest) {
        Category category = modelMapper.map(categoryRequest, Category.class);
        category = categoryRepository.save(category);

        return modelMapper.map(category, CategoryDto.class);
    }

    @Transactional
    public CategoryDto editCategory(Long categoryId, CategoryRequest categoryRequest) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(EntityNotFoundException::new);
        modelMapper.map(categoryRequest, category);
        category = categoryRepository.save(category);

        return modelMapper.map(category, CategoryDto.class);
    }

    @Transactional
    public void deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(EntityNotFoundException::new);
        categoryRepository.delete(category);
    }
}
