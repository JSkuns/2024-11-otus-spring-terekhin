package ru.otus.hw.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.dto.mappers.impl.AuthorDtoMapper;
import ru.otus.hw.dto.mappers.impl.BookDtoMapper;
import ru.otus.hw.dto.mappers.impl.CommentDtoMapper;
import ru.otus.hw.dto.mappers.impl.GenreDtoMapper;
import ru.otus.hw.dto.models.author.AuthorDto;
import ru.otus.hw.dto.models.book.BookDto;
import ru.otus.hw.dto.models.book.BookUpdateDto;
import ru.otus.hw.dto.models.comment.CommentCreateDto;
import ru.otus.hw.dto.models.comment.CommentDto;
import ru.otus.hw.dto.models.comment.CommentUpdateDto;
import ru.otus.hw.dto.models.genre.GenreDto;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Comment;
import ru.otus.hw.models.Genre;
import ru.otus.hw.services.impl.CommentServiceImpl;

import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@Import({CommentServiceImpl.class, CommentDtoMapper.class, BookDtoMapper.class, AuthorDtoMapper.class, GenreDtoMapper.class})
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CommentServiceImplTest {

    @Autowired
    private CommentService commentService;

    private List<BookDto> dbBooks;

    @BeforeEach
    void setUp() {
        List<AuthorDto> dbAuthors = getDbAuthors();
        List<GenreDto> dbGenres = getDbGenres();
        dbBooks = getDbBooks(dbAuthors, dbGenres);
    }

    @ParameterizedTest
    @MethodSource("getDbComments")
    @DisplayName("должен загружать комментарий по id")
    void shouldReturnCorrectCommentById(CommentDto expectedComment) {
        var actualComment = commentService.findById(expectedComment.getId());

        assertThat(actualComment)
                .usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .isEqualTo(expectedComment);
    }

    @Test
    @DisplayName("должен сохранять новый комментарий")
    void shouldSaveNewComment() {
        var expectedComment = new CommentCreateDto(dbBooks.get(0).getId(), "CommentText_10500");
        var returnedComment = commentService.create(expectedComment);

        assertThat(returnedComment)
                .isNotNull()
                .matches(comment -> comment.getId() != 0)
                .usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .isEqualTo(expectedComment);

        assertThat(commentService.findById(returnedComment.getId()))
                .usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .isEqualTo(returnedComment);
    }

    @Test
    @DisplayName("должен сохранять измененный комментарий")
    void shouldSaveUpdatedComment() {
        var expectedDto = new CommentDto(1, dbBooks.get(1), "BookTitle_10500");
        var expectedUpdateDto = new CommentUpdateDto(expectedDto.getId(), expectedDto.getText());

        assertThat(commentService.findById(expectedDto.getId()))
                .isNotEqualTo(expectedDto);

        var returnedComment = commentService.update(expectedUpdateDto);
        assertThat(returnedComment).isNotNull()
                .matches(comment -> comment.getId() != 0)
                .usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .ignoringFields("book")
                .isEqualTo(expectedDto);

        assertThat(commentService.findById(returnedComment.getId()))
                .usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .isEqualTo(returnedComment);
    }

    @Test
    @DisplayName("должен удалять комментарий по id ")
    void shouldDeleteComment() {
        var testId = 1;

        assertThat(commentService.findById(testId)).isNotNull();

        commentService.deleteById(testId);

        assertThatThrownBy(() -> commentService.findById(testId)).isInstanceOf(NullPointerException.class);
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

    private static List<CommentDto> getDbComments(List<BookDto> dbBooks) {
        return IntStream
                .range(1, 4)
                .boxed()
                .map(id -> new CommentDto(id, dbBooks.get(id - 1), "Text_" + id))
                .toList();
    }

    private static List<CommentDto> getDbComments() {
        var dbAuthors = getDbAuthors();
        var dbGenres = getDbGenres();
        var dbBooks = getDbBooks(dbAuthors, dbGenres);
        return getDbComments(dbBooks);
    }

}