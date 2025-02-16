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
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.models.Genre;
import ru.otus.hw.services.impl.CommentServiceImpl;

import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(CommentServiceImpl.class)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CommentServiceImplTest {

    @Autowired
    private CommentService commentService;

    private List<Book> dbBooks;

    @BeforeEach
    void setUp() {
        List<Author> dbAuthors = getDbAuthors();
        List<Genre> dbGenres = getDbGenres();
        dbBooks = getDbBooks(dbAuthors, dbGenres);
    }

    @ParameterizedTest
    @MethodSource("getDbComments")
    @DisplayName("должен загружать комментарий по id")
    void shouldReturnCorrectCommentById(Comment expectedComment) {
        var actualComment = commentService.findById(expectedComment.getId());

        assertThat(actualComment)
                .isPresent()
                .get()
                .usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .ignoringFields("book")
                .isEqualTo(expectedComment);
    }

    @Test
    @DisplayName("должен сохранять новый комментарий")
    void shouldSaveNewComment() {
        var expectedComment = new Comment(0, "CommentText_10500", dbBooks.get(0));
        var returnedComment = commentService
                .insert(expectedComment.getBook().getId(), expectedComment.getText());

        assertThat(returnedComment)
                .isNotNull()
                .matches(comment -> comment.getId() != 0)
                .usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .ignoringFields("id")
                .isEqualTo(expectedComment);

        assertThat(commentService.findById(returnedComment.getId()))
                .isPresent()
                .get()
                .usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .ignoringFields("book")
                .isEqualTo(returnedComment);
    }

    @Test
    @DisplayName("должен сохранять измененный комментарий")
    void shouldSaveUpdatedComment() {
        var expectedComment = new Comment(1, "BookTitle_10500", dbBooks.get(1));

        assertThat(commentService.findById(expectedComment.getId()))
                .isPresent()
                .get()
                .isNotEqualTo(expectedComment);

        var returnedComment = commentService
                .update(expectedComment.getId(), expectedComment.getText());
        assertThat(returnedComment).isNotNull()
                .matches(comment -> comment.getId() != 0)
                .usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .ignoringFields("book")
                .isEqualTo(expectedComment);

        assertThat(commentService.findById(returnedComment.getId()))
                .isPresent()
                .get()
                .usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .ignoringFields("book")
                .isEqualTo(returnedComment);
    }

    @Test
    @DisplayName("должен удалять комментарий по id ")
    void shouldDeleteComment() {
        var testId = 1;

        assertThat(commentService.findById(testId))
                .isPresent();

        commentService.deleteById(testId);

        assertThat(commentService.findById(testId))
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

    private static List<Comment> getDbComments(List<Book> dbBooks) {
        return IntStream
                .range(1, 4)
                .boxed()
                .map(id -> new Comment(id, "Text_" + id, dbBooks.get(id - 1)))
                .toList();
    }

    private static List<Comment> getDbComments() {
        var dbAuthors = getDbAuthors();
        var dbGenres = getDbGenres();
        var dbBooks = getDbBooks(dbAuthors, dbGenres);
        return getDbComments(dbBooks);
    }

}