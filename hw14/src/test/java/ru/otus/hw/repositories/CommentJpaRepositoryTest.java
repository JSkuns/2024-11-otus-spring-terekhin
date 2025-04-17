package ru.otus.hw.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.models.h2.Author;
import ru.otus.hw.models.h2.Book;
import ru.otus.hw.models.h2.Comment;
import ru.otus.hw.models.h2.Genre;
import ru.otus.hw.repositories.h2.CommentRepository;

import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CommentJpaRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    private List<Book> dbBooks;

    @BeforeEach
    void setUp() {
        List<Author> dbAuthors = getDbAuthors();
        List<Genre> dbGenres = getDbGenres();
        dbBooks = getDbBooks(dbAuthors, dbGenres);
    }

    @Test
    @DisplayName("Метод 'findById()'. В БД нет комментария с ID '-777'")
    void emptyWhenNotFoundCommentByIdTest() {
        // Assert
        assertThat(commentRepository.findById(-777L))
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
            var actualComment = commentRepository.findById(id.longValue());
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
            var comments = commentRepository
                    .findByBookId(id);
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
        var expectedComment = new Comment(0, "Text_444", dbBooks.get(0));
        // Act
        var returnedComment = commentRepository.save(expectedComment);
        // Assert
        assertThat(returnedComment)
                .isNotNull()
                .matches(comment -> comment.getId() > 0)
                .usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .isEqualTo(expectedComment);
        assertThat(commentRepository.findById(returnedComment.getId()))
                .isPresent();
    }

    @Test
    @DisplayName("Метод 'save()'. Должны измениться данные в комментарии c ID '2'")
    void updateExistedCommentTest() {
        // Arrange
        var expectedComment = new Comment(1L, "Text_555", dbBooks.get(1));
        assertThat(commentRepository.findById(expectedComment.getId()))
                .isPresent()
                .get()
                .isNotEqualTo(expectedComment);
        // Act
        var returnedComment = commentRepository.save(expectedComment);
        // Assert
        assertThat(returnedComment)
                .isNotNull()
                .matches(comment -> comment.getId() > 0)
                .usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .isEqualTo(expectedComment);
        assertThat(commentRepository.findById(returnedComment.getId()))
                .isPresent();
    }

    @Test
    @DisplayName("Метод 'delete()'. Должен удалиться комментарий из БД")
    void deleteBookTest() {
        // Act
        assertThat(commentRepository.findById(1L))
                .isPresent();
        commentRepository.deleteById(1L);
        // Assert
        assertThat(commentRepository.findById(1L))
                .isEmpty();
    }

    private static List<Author> getDbAuthors() {
        return IntStream.range(1, 4).boxed()
                .map(id -> new Author(id, "Author_" + id))
                .toList();
    }

    private static List<Genre> getDbGenres() {
        return IntStream.range(1, 4).boxed()
                .map(id -> new Genre(id, "Genre_" + id))
                .toList();
    }

    private static List<Book> getDbBooks(List<Author> dbAuthors, List<Genre> dbGenres) {
        return IntStream.range(1, 4).boxed()
                .map(id -> new Book(id, "BookTitle_" + id, dbAuthors.get(id - 1), dbGenres.get(id - 1)))
                .toList();
    }

}
