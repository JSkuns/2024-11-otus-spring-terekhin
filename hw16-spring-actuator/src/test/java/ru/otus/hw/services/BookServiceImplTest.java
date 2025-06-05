package ru.otus.hw.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.hw.dto.mappers.impl.AuthorDtoMapper;
import ru.otus.hw.dto.mappers.impl.BookDtoMapper;
import ru.otus.hw.dto.mappers.impl.GenreDtoMapper;
import ru.otus.hw.dto.models.author.AuthorDto;
import ru.otus.hw.dto.models.book.BookCreateDto;
import ru.otus.hw.dto.models.book.BookDto;
import ru.otus.hw.dto.models.book.BookUpdateDto;
import ru.otus.hw.dto.models.genre.GenreDto;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;
import ru.otus.hw.services.impl.BookServiceImpl;

import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@Import({BookServiceImpl.class, BookDtoMapper.class, AuthorDtoMapper.class, GenreDtoMapper.class})
public class BookServiceImplTest {

    @Autowired
    private BookService bookService;

    private List<AuthorDto> dbAuthors;

    private List<GenreDto> dbGenres;

    private List<BookDto> dbBooks;

    @BeforeEach
    void setUp() {
        dbAuthors = getDbAuthors();
        dbGenres = getDbGenres();
        dbBooks = getDbBooks(dbAuthors, dbGenres);
    }

    @ParameterizedTest
    @MethodSource("getDbBooks")
    @DisplayName("должен загружать книгу по id")
    void shouldReturnCorrectBookById(BookDto expectedBook) {
        var actualBook = bookService.findById(expectedBook.getId());

        assertThat(actualBook)
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
        var expectedBook = new BookCreateDto( "BookTitle_10500", dbAuthors.get(0).getId(), dbGenres.get(0).getId());
        var returnedBook = bookService.create(expectedBook);

        assertThat(returnedBook)
                .isNotNull()
                .matches(book -> book.getId() != 0)
                .usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .ignoringFields("id")
                .isEqualTo(expectedBook);

        assertThat(bookService.findById(returnedBook.getId()))
                .usingRecursiveComparison()
                .isEqualTo(returnedBook);
    }

    @Test
    @DisplayName("должен сохранять измененную книгу")
    void shouldSaveUpdatedBook() {
        var expectedDto = new BookDto(1, "BookTitle_10500", dbAuthors.get(2), dbGenres.get(2));
        var expectedUpdateDto = new BookUpdateDto(expectedDto.getId(), expectedDto.getTitle(), expectedDto.getAuthor().getId(), expectedDto.getGenre().getId());

        assertThat(bookService.findById(expectedDto.getId()))
                .isNotEqualTo(expectedDto);

        var returnedBook = bookService.update(expectedUpdateDto);
        assertThat(returnedBook).isNotNull()
                .matches(book -> book.getId() != 0)
                .usingRecursiveComparison()
                .isEqualTo(expectedDto);

        assertThat(bookService.findById(returnedBook.getId()))
                .usingRecursiveComparison()
                .isEqualTo(returnedBook);
    }

    @Test
    @DisplayName("должен удалять книгу по id ")
    void shouldDeleteBook() {
        var testId = 1;

        assertThat(bookService.findById(testId)).isNotNull();

        bookService.deleteById(testId);

        assertThatThrownBy(() -> bookService.findById(testId)).isInstanceOf(NullPointerException.class);
    }

    private static List<AuthorDto> getDbAuthors() {
        return IntStream
                .range(1, 4)
                .boxed()
                .map(id -> new AuthorDto(id, "Author_" + id))
                .toList();
    }

    private static List<GenreDto> getDbGenres() {
        return IntStream
                .range(1, 4)
                .boxed()
                .map(id -> new GenreDto(id, "Genre_" + id))
                .toList();
    }

    private static List<BookDto> getDbBooks(List<AuthorDto> dbAuthors, List<GenreDto> dbGenres) {
        return IntStream
                .range(1, 4)
                .boxed()
                .map(id -> new BookDto(id, "BookTitle_" + id, dbAuthors.get(id - 1), dbGenres.get(id - 1)))
                .toList();
    }

    private static List<BookDto> getDbBooks() {
        var dbAuthors = getDbAuthors();
        var dbGenres = getDbGenres();
        return getDbBooks(dbAuthors, dbGenres);
    }

}