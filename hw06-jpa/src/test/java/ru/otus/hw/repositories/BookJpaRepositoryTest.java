package ru.otus.hw.repositories;

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
@Import({BookJpaRepository.class, GenreJpaRepository.class, AuthorJpaRepository.class})
public class BookJpaRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    @DisplayName("Метод 'findById()'. В БД нет книги с ID '-777'")
    void emptyWhenNotFoundBookByIdTest() {
        // Assert
        Assertions.assertEquals(Optional.empty(), bookRepository.findById(-777));
    }

    @Test
    @DisplayName("Метод 'findById()'. В БД есть книга с ID '1'")
    void hasBookByIdTest() {
        // Assert
        Assertions.assertNotNull(bookRepository.findById(1));
    }

    @Test
    @DisplayName("Метод 'findAll()'. В БД нет книги с ID '-888'")
    void inAllBooksHasNotAuthorTest() {
        // Act
        var booksList = bookRepository.findAll();
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
        var booksList = bookRepository.findAll();
        // Assert
        Assertions.assertTrue(
                booksList
                        .stream()
                        .map(Book::getId)
                        .anyMatch(elt -> elt == 2)
        );
    }

    @Test
    @DisplayName("Метод 'save()'. Должна добавиться новая книга в БД")
    void insertNewBookTest() {
        // Arrange
        var testTitleOfBook = "Peter Pan";
        var genre = genreRepository.findById(2).orElse(null);
        var author = authorRepository.findById(3).orElse(null);
        var book = new Book(0, testTitleOfBook, author, genre);
        var initCountBooks = bookRepository.findAll().size();
        // Act
        bookRepository.save(book);
        var booksList = bookRepository.findAll();
        var countOfBooksAfterSave = booksList.size();
        // Assert
        Assertions.assertTrue(initCountBooks < countOfBooksAfterSave);
        Assertions.assertTrue(
                booksList
                        .stream()
                        .map(Book::getTitle)
                        .anyMatch(elt -> Objects.equals(elt, testTitleOfBook))
        );
    }

    @Test
    @DisplayName("Метод 'save()'. Должны измениться данные о книге")
    void updateExistedBookTest() {
        // Arrange
        var testTitleOfBook = "Wuthering Heights";
        var existedBook = bookRepository.findById(3).orElse(new Book());
        var initTitleOfBook = existedBook.getTitle();
        // Act
        existedBook.setTitle(testTitleOfBook);
        bookRepository.save(existedBook);
        var resultBook = bookRepository.findById(existedBook.getId()).orElse(new Book());
        // Assert
        Assertions.assertNotEquals(initTitleOfBook, resultBook.getTitle());
        Assertions.assertEquals(testTitleOfBook, resultBook.getTitle());
    }

    @Test
    @DisplayName("Метод 'delete()'. Должна удалиться книга из БД")
    void deleteBookTest() {
        // Arrange
        var booksList = bookRepository.findAll();
        var initCountBooks = booksList.size();
        var firstBook = booksList.stream().findFirst().orElse(new Book());
        // Act
        bookRepository.delete(firstBook);
        // Assert
        Assertions.assertNotEquals(initCountBooks, bookRepository.findAll().size());
    }

}
