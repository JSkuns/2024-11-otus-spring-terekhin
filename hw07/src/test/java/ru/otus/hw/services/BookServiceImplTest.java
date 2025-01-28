package ru.otus.hw.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.hw.models.Book;

import java.util.Objects;
import java.util.Optional;

@DataJpaTest
@Import(BookServiceImpl.class)
public class BookServiceImplTest {

    @Autowired
    private BookService bookService;

    @Test
    @DisplayName("Метод 'findById()'. В БД нет книги с ID '-777'")
    void emptyWhenNotFoundBookByIdTest() {
        // Assert
        Assertions.assertEquals(Optional.empty(), bookService.findById(-777));
    }

    @Test
    @DisplayName("Метод 'findById()'. В БД есть книга с ID '1'")
    void hasBookByIdTest() {
        // Assert
        Assertions.assertNotNull(bookService.findById(1));
    }

    @Test
    @DisplayName("Метод 'findAll()'. В БД нет книги с ID '-888'")
    void inAllBooksHasNotAuthorTest() {
        // Act
        var booksList = bookService.findAll();
        // Assert
        Assertions.assertFalse(
                booksList
                        .stream()
                        .map(Book::getId)
                        .anyMatch(elt -> elt == -888)
        );
    }

    @Test
    @DisplayName("Метод 'findAll()'. В БД есть книга с ID '2'")
    void inAllBooksHasBookTest() {
        // Act
        var booksList = bookService.findAll();
        // Assert
        Assertions.assertTrue(
                booksList
                        .stream()
                        .map(Book::getId)
                        .anyMatch(elt -> elt == 2)
        );
    }

    @Test
    @DisplayName("Метод 'insert()'. Должна добавиться новая книга в БД")
    void insertNewBookTest() {
        // Arrange
        var testTitleOfBook = "Peter Pan";
        var initCountBooks = bookService.findAll().size();
        // Act
        bookService.insert(testTitleOfBook, 2, 3);
        var resultBooksList = bookService.findAll();
        // Assert
        Assertions.assertTrue(initCountBooks < resultBooksList.size());
        Assertions.assertTrue(
                resultBooksList
                        .stream()
                        .map(Book::getTitle)
                        .anyMatch(elt -> Objects.equals(elt, testTitleOfBook))
        );
    }

    @Test
    @DisplayName("Метод 'update()'. Должны измениться данные о книге")
    void updateExistedBookTest() {
        // Arrange
        var testTitleOfBook = "Wuthering Heights";
        var testIdOfBook = 2;
        var initTitleOfBook = bookService.findById(testIdOfBook).orElse(new Book()).getTitle();
        // Act
        bookService.update(testIdOfBook, testTitleOfBook, 1, 3);
        var resultBook = bookService.findById(testIdOfBook).orElse(new Book());
        // Assert
        Assertions.assertNotEquals(initTitleOfBook, resultBook.getTitle());
        Assertions.assertEquals(testTitleOfBook, resultBook.getTitle());
    }

    @Test
    @DisplayName("Метод 'deleteById()'. Должна удалиться книга из БД")
    void deleteBookTest() {
        // Arrange
        var booksList = bookService.findAll();
        var initCountBooks = booksList.size();
        var firstBookFromDb = booksList.stream().findFirst().orElse(new Book());
        // Act
        bookService.deleteById(firstBookFromDb.getId());
        // Assert
        Assertions.assertNotEquals(initCountBooks, bookService.findAll().size());
    }

}
