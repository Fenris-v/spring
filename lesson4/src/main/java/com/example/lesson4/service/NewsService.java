package com.example.lesson4.service;

import com.example.lesson4.dto.request.NewsFilter;
import com.example.lesson4.dto.request.NewsRequest;
import com.example.lesson4.dto.response.NewsDto;
import com.example.lesson4.exception.EntityNotFoundException;
import com.example.lesson4.exception.ForbiddenActionException;
import com.example.lesson4.model.Category;
import com.example.lesson4.model.News;
import com.example.lesson4.model.User;
import com.example.lesson4.repository.CategoryRepository;
import com.example.lesson4.repository.NewsRepository;
import com.example.lesson4.repository.NewsSpecification;
import com.example.lesson4.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NewsService {
    private final ModelMapper modelMapper;
    private final NewsRepository newsRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @PostConstruct
    private void configureMappings() {
        modelMapper.addMappings(new CategoryPropertyMap());
    }

    public Page<NewsDto> getNews(Long categoryId, int page, int perPage) {
        Page<News> news =
                newsRepository.findAllByCategory_IdOrderByCreatedAtDesc(categoryId, PageRequest.of(page, perPage));

        return news.map((element) -> modelMapper.map(element, NewsDto.class));
    }

    public NewsDto getNewsById(Long newsId) {
        News news = newsRepository.findFirstById(newsId).orElseThrow(EntityNotFoundException::new);
        return modelMapper.map(news, NewsDto.class);
    }

    @Transactional
    public NewsDto createNews(Long userId, NewsRequest newsRequest) {
        News news = modelMapper.map(newsRequest, News.class);
        Category category =
                categoryRepository.findById(newsRequest.getCategoryId()).orElseThrow(EntityNotFoundException::new);
        User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        news.setId(null);
        news.setUser(user);
        news.setCategory(category);

        news = newsRepository.save(news);

        return modelMapper.map(news, NewsDto.class);
    }

    @Transactional
    public NewsDto editNews(Long userId, Long newsId, NewsRequest newsRequest) {
        News news = newsRepository.findById(newsId).orElseThrow(EntityNotFoundException::new);
        if (!news.getUser().getId().equals(userId)) {
            throw new ForbiddenActionException();
        }

        modelMapper.map(newsRequest, news);
        news.setId(newsId);
        if (!news.getCategory().getId().equals(newsRequest.getCategoryId())) {
            Category category =
                    categoryRepository.findById(newsRequest.getCategoryId()).orElseThrow(EntityNotFoundException::new);
            news.setCategory(category);
        }

        newsRepository.save(news);
        return modelMapper.map(news, NewsDto.class);
    }

    @Transactional
    public void deleteNews(Long userId, Long newsId) {
        News news = newsRepository.findById(newsId).orElseThrow(EntityNotFoundException::new);
        if (!news.getUser().getId().equals(userId)) {
            throw new ForbiddenActionException();
        }

        newsRepository.delete(news);
    }

    public Page<NewsDto> getFilteredNews(@NonNull NewsFilter newsFilter) {
        PageRequest pageRequest = PageRequest.of(newsFilter.getPage(), newsFilter.getPerPage());
        Page<News> news = newsRepository.findAll(NewsSpecification.withFilter(newsFilter), pageRequest);

        return news.map((element) -> modelMapper.map(element, NewsDto.class));
    }
}

class CategoryPropertyMap extends PropertyMap<NewsRequest, News> {
    @Override
    protected void configure() {
        skip(destination.getCategory());
    }
}
