package com.example.lesson4.repository;

import com.example.lesson4.dto.request.NewsFilter;
import com.example.lesson4.model.News;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;

public interface NewsSpecification {
    static Specification<News> withFilter(@NonNull NewsFilter newsFilter) {
        return Specification.where(byUserId(newsFilter.getUserId())).and(byCategoryId(newsFilter.getCategoryId()));
    }

    static Specification<News> byUserId(Long userId) {
        return (root, query, criteriaBuilder) -> {
            if (userId == null) {
                return null;
            }

            return criteriaBuilder.equal(root.get("user").get("id"), userId);
        };
    }

    static Specification<News> byCategoryId(Long categoryId) {
        return (root, query, criteriaBuilder) -> {
            if (categoryId == null) {
                return null;
            }

            return criteriaBuilder.equal(root.get("category").get("id"), categoryId);
        };
    }
}
