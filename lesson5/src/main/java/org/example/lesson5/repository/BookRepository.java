package org.example.lesson5.repository;

import org.example.lesson5.model.Book;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    @EntityGraph(attributePaths = {"category"})
    Optional<Book> findFirstById(Long id);

    @EntityGraph(attributePaths = {"category"})
    @Query("from Book where name ilike concat('%', :name, '%') and author ilike concat('%', :author, '%')")
    List<Book> findByNameAndAuthor(String name, String author);
}
