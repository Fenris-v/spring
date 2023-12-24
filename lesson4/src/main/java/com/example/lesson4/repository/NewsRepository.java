package com.example.lesson4.repository;

import com.example.lesson4.model.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface NewsRepository extends JpaRepository<News, Long>, JpaSpecificationExecutor<News> {
    @EntityGraph(attributePaths = {"user", "category", "comments"})
    Optional<News> findFirstById(Long newsId);

    Page<News> findAllByCategory_IdOrderByCreatedAtDesc(Long categoryId, Pageable page);
}
