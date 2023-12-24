package org.example.lesson5.repository;

import org.example.lesson5.model.Category;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @EntityGraph(attributePaths = {"books"})
    Optional<Category> findByNameIgnoreCase(String categoryName);
}
