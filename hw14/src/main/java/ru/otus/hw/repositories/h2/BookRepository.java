package ru.otus.hw.repositories.h2;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.hw.models.h2.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    @EntityGraph(value = "book-entity-graph")
    Optional<Book> findById(long id);

    @Override
    @EntityGraph(value = "book-entity-graph")
    List<Book> findAll();

}
