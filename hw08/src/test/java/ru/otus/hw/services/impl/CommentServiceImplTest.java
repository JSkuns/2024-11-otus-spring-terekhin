package ru.otus.hw.services.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.models.Genre;
import ru.otus.hw.services.CommentService;

import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
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

    @Test
    @DisplayName("Метод 'findById()'. В БД нет комментария с ID '-777'")
    void emptyWhenNotFoundCommentByIdTest(@Autowired MongoTemplate mongoTemplate) {
        // Assert
        assertThat(commentService.findById("-777"))
                .isEmpty();
    }

    @Test
    @DisplayName("Метод 'findById()'. В БД есть комментарии")
    void hasCommentByIdTest() {
        // Arrange
        var commentIdsList = IntStream.range(1, 3)
                .boxed()
                .toList();
        // Act
        commentIdsList.forEach(id -> {
            var actualComment = commentService.findById(String.valueOf(id));
            // Assert
            assertThat(actualComment)
                    .isPresent();
        });
    }

    @Test
    @DisplayName("Метод 'findAllCommentsByBookId()'. У книг есть комментарии")
    void bookHasNotCommentTest() {
        // Arrange
        var bookIdsList = dbBooks
                .stream()
                .map(Book::getId);
        // Act
        bookIdsList.forEach(id -> {
            var expectedCommentText = "Text_" + id;
            var comments = commentService
                    .findAllCommentsByBookId(id);
            var commentTexts = comments
                    .stream()
                    .map(Comment::getText);
            // Assert
            assertThat(comments)
                    .isNotNull();
            assertThat(commentTexts)
                    .contains(expectedCommentText);
        });
    }

    @Test
    @DisplayName("Метод 'save()'. Должен добавиться новый комментарий к книге с ID '1'")
    void insertNewCommentTest() {
        // Arrange
        var expectedComment = new Comment("4", "Text_444", dbBooks.get(0));
        // Act
        var returnedComment = commentService
                .insert(expectedComment.getBook().getId(), expectedComment.getText());
        // Assert
        assertThat(returnedComment)
                .isNotNull()
                .matches(comment -> comment.getId() != null)
                .usingRecursiveComparison()
                .ignoringExpectedNullFields();
        assertThat(commentService.findById(returnedComment.getId()))
                .isPresent();
    }

    @Test
    @DisplayName("Метод 'save()'. Должны измениться данные в комментарии c ID '2'")
    void updateExistedCommentTest() {
        // Arrange
        var expectedComment = new Comment("1", "Text_555", dbBooks.get(1));
        assertThat(commentService.findById(expectedComment.getId()))
                .isPresent()
                .get()
                .isNotEqualTo(expectedComment);
        // Act
        var returnedComment = commentService.update(expectedComment.getId(), expectedComment.getText());
        // Assert
        assertThat(returnedComment)
                .isNotNull()
                .matches(comment -> !comment.getId().equals("0"));
        assertThat(commentService.findById(returnedComment.getId()))
                .isPresent();
    }

    @Test
    @DisplayName("Метод 'delete()'. Должен удалиться комментарий из БД")
    void deleteBookTest() {
        // Act
        assertThat(commentService.findById("1"))
                .isPresent();
        commentService.deleteById("1");
        // Assert
        assertThat(commentService.findById("1"))
                .isEmpty();
    }

    private static List<Author> getDbAuthors() {
        return IntStream.range(1, 4).boxed()
                .map(id -> new Author(String.valueOf(id), "Author_" + id))
                .toList();
    }

    private static List<Genre> getDbGenres() {
        return IntStream.range(1, 4).boxed()
                .map(id -> new Genre(String.valueOf(id), "Genre_" + id))
                .toList();
    }

    private static List<Book> getDbBooks(List<Author> dbAuthors, List<Genre> dbGenres) {
        return IntStream.range(1, 4).boxed()
                .map(id -> new Book(String.valueOf(id), "BookTitle_" + id, dbAuthors.get(id - 1), dbGenres.get(id - 1)))
                .toList();
    }

}
