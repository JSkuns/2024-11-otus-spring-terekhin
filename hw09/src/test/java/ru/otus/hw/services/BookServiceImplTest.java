package ru.otus.hw.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;
import ru.otus.hw.services.impl.BookServiceImpl;

import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(BookServiceImpl.class)
public class BookServiceImplTest {

    @Autowired
    private BookService bookService;

    private List<Author> dbAuthors;

    private List<Genre> dbGenres;

    private List<Book> dbBooks;

    @BeforeEach
    void setUp() {
        dbAuthors = getDbAuthors();
        dbGenres = getDbGenres();
        dbBooks = getDbBooks(dbAuthors, dbGenres);
    }

    @ParameterizedTest
    @MethodSource("getDbBooks")
    @DisplayName("должен загружать книгу по id")
    void shouldReturnCorrectBookById(Book expectedBook) {
        var actualBook = bookService.findById(expectedBook.getId());

        assertThat(actualBook)
                .isPresent()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(expectedBook);
    }

    @Test
    @DisplayName("должен загружать список всех книг")
    void shouldReturnCorrectBooksList() {
        var actualBooks = bookService.findAll();
        var expectedBooks = dbBooks;

        assertThat(actualBooks)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields()
                .containsExactlyElementsOf(expectedBooks);
    }

    @Test
    @DisplayName("должен сохранять новую книгу")
    void shouldSaveNewBook() {
        var expectedBook = new Book(0, "BookTitle_10500", dbAuthors.get(0), dbGenres.get(0));
        var returnedBook = bookService
                .insert(expectedBook.getTitle(), expectedBook.getAuthor().getId(), expectedBook.getGenre().getId());

        assertThat(returnedBook)
                .isNotNull()
                .matches(book -> book.getId() != 0)
                .usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .ignoringFields("id")
                .isEqualTo(expectedBook);

        assertThat(bookService.findById(returnedBook.getId()))
                .isPresent()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(returnedBook);
    }

    @Test
    @DisplayName("должен сохранять измененную книгу")
    void shouldSaveUpdatedBook() {
        var expectedBook = new Book(1, "BookTitle_10500", dbAuthors.get(2), dbGenres.get(2));

        assertThat(bookService.findById(expectedBook.getId()))
                .isPresent()
                .get()
                .isNotEqualTo(expectedBook);

        var returnedBook = bookService
                .update(expectedBook.getId(), expectedBook.getTitle(), expectedBook.getAuthor().getId(), expectedBook.getGenre().getId());
        assertThat(returnedBook).isNotNull()
                .matches(book -> book.getId() != 0)
                .usingRecursiveComparison()
                .isEqualTo(expectedBook);

        assertThat(bookService.findById(returnedBook.getId()))
                .isPresent()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(returnedBook);
    }

    @Test
    @DisplayName("должен удалять книгу по id ")
    void shouldDeleteBook() {
        var testId = 1;

        assertThat(bookService.findById(testId))
                .isPresent();

        bookService.deleteById(testId);

        assertThat(bookService.findById(testId))
                .isEmpty();
    }

    private static List<Author> getDbAuthors() {
        return IntStream
                .range(1, 4)
                .boxed()
                .map(id -> new Author(id, "Author_" + id))
                .toList();
    }

    private static List<Genre> getDbGenres() {
        return IntStream
                .range(1, 4)
                .boxed()
                .map(id -> new Genre(id, "Genre_" + id))
                .toList();
    }

    private static List<Book> getDbBooks(List<Author> dbAuthors, List<Genre> dbGenres) {
        return IntStream
                .range(1, 4)
                .boxed()
                .map(id -> new Book(id, "BookTitle_" + id, dbAuthors.get(id - 1), dbGenres.get(id - 1)))
                .toList();
    }

    private static List<Book> getDbBooks() {
        var dbAuthors = getDbAuthors();
        var dbGenres = getDbGenres();
        return getDbBooks(dbAuthors, dbGenres);
    }

}