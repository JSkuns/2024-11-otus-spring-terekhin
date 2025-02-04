package ru.otus.hw.repositories;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;

import java.util.Objects;
import java.util.Optional;

@DataJpaTest
@Import({CommentJpaRepository.class, BookJpaRepository.class})
public class CommentJpaRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private BookRepository bookRepository;

    @Test
    @DisplayName("Метод 'findById()'. В БД нет комментария с ID '-777'")
    void emptyWhenNotFoundCommentByIdTest() {
        // Assert
        Assertions.assertEquals(Optional.empty(), commentRepository.findById(-777));
    }

    @Test
    @DisplayName("Метод 'findById()'. В БД есть комментарий с ID '1'")
    void hasCommentByIdTest() {
        // Assert
        Assertions.assertNotNull(commentRepository.findById(1));
    }

    @Test
    @DisplayName("Метод 'findAllCommentsByBookId()'. Комментария с ID '-999' нет у книги с ID '1'")
    void bookHasNotCommentTest() {
        // Act
        var commentsList = commentRepository.findAllCommentsByBookId(1);
        // Assert
        Assertions.assertFalse(
                commentsList
                        .stream()
                        .map(Comment::getId)
                        .anyMatch(elt -> elt == -999)
        );
    }

    @Test
    @DisplayName("Метод 'findAllCommentsByBookId()'. Комментарий с ID '2' есть у книги c ID '2'")
    void bookHasCommentTest() {
        // Act
        var commentsList = commentRepository.findAllCommentsByBookId(2);
        // Assert
        Assertions.assertTrue(
                commentsList
                        .stream()
                        .map(Comment::getId)
                        .anyMatch(elt -> elt == 2)
        );
    }

    @Test
    @DisplayName("Метод 'save()'. Должен добавиться новый комментарий к книге с ID '1'")
    void insertNewCommentTest() {
        // Arrange
        var testBookId = 1;
        var testCommentText = "Test_1234567890";
        var book = bookRepository.findById(testBookId).orElse(new Book());
        var comment = new Comment(0, testCommentText, book);
        var initCountOfCommentsByBook = commentRepository.findAllCommentsByBookId(testBookId).size();
        // Act
        commentRepository.save(comment);
        var commentsList = commentRepository.findAllCommentsByBookId(testBookId);
        var countOfCommentsAfterSave = commentsList.size();
        // Assert
        Assertions.assertTrue(initCountOfCommentsByBook < countOfCommentsAfterSave);
        Assertions.assertTrue(
                commentsList
                        .stream()
                        .map(Comment::getText)
                        .anyMatch(elt -> Objects.equals(elt, testCommentText))
        );
    }

    @Test
    @DisplayName("Метод 'save()'. Должны измениться данные в комментарии c ID '2'")
    void updateExistedCommentTest() {
        // Arrange
        var testCommentText = "Test_qqq_www_eee_rrr_ttt_yyy";
        var existedComment = commentRepository.findById(2).orElse(new Comment());
        var initTextOfComment = existedComment.getText();
        // Act
        existedComment.setText(testCommentText);
        commentRepository.save(existedComment);
        var resultComment = commentRepository.findById(existedComment.getId()).orElse(new Comment());
        // Assert
        Assertions.assertNotEquals(initTextOfComment, resultComment.getText());
        Assertions.assertEquals(testCommentText, resultComment.getText());
    }

    @Test
    @DisplayName("Метод 'delete()'. Должен удалиться комментарий из БД")
    void deleteBookTest() {
        // Arrange
        var testBookId = 3;
        var commentsList = commentRepository.findAllCommentsByBookId(testBookId);
        var initCountComments = commentsList.size();
        var firstComment = commentsList.stream().findFirst().orElse(new Comment());
        // Act
        commentRepository.delete(firstComment);
        // Assert
        Assertions.assertNotEquals(initCountComments, commentRepository.findAllCommentsByBookId(testBookId).size());
    }

}
