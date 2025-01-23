package ru.otus.hw.repositories;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.hw.models.Book;

import java.util.Optional;

@DataJpaTest
@Import(JpaBookRepository.class)
public class JpaBookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    @DisplayName("Метод 'findById()'. В БД нет книги с ID '-777'")
    void emptyWhenNotFoundBookById() {
        // Assert
        Assertions.assertEquals(Optional.empty(), bookRepository.findById(-777));
    }

    @Test
    @DisplayName("Метод 'findById()'. В БД есть книга с ID '1'")
    void hasBookById() {
        // Assert
        Assertions.assertNotNull(bookRepository.findById(1));
    }

    @Test
    @DisplayName("Метод 'findAll()'. В БД нет книги с ID '-888'")
    void inAllBooksHasNotAuthor() {
        // Arrange
        var expectedBook = new Book(-888, null, null, null);
        // Act
        var booksList = bookRepository.findAll();
        // Assert
        Assertions.assertFalse(
                booksList
                        .stream()
                        .map(Book::getId)
                        .anyMatch(elt -> elt == expectedBook.getId())
        );
    }

    @Test
    @DisplayName("Метод 'findAll()'. В БД есть книга с ID '2'")
    void inAllBooksHasBook() {
        // Arrange
        var expectedBook = new Book(2, null, null, null);
        // Act
        var booksList = bookRepository.findAll();
        // Assert
        Assertions.assertTrue(
                booksList
                        .stream()
                        .map(Book::getId)
                        .anyMatch(elt -> elt == expectedBook.getId())
        );
    }

}
