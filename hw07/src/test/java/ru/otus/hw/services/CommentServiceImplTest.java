package ru.otus.hw.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.hw.models.Comment;

import java.util.Objects;
import java.util.Optional;

@DataJpaTest
@Import(CommentServiceImpl.class)
public class CommentServiceImplTest {

    @Autowired
    private CommentService commentService;

    @Test
    @DisplayName("Метод 'findById()'. В БД нет комментария с ID '-777'")
    void emptyWhenNotFoundCommentByIdTest() {
        // Assert
        Assertions.assertEquals(Optional.empty(), commentService.findById(-777));
    }

    @Test
    @DisplayName("Метод 'findById()'. В БД есть комментарий с ID '1'")
    void hasCommentByIdTest() {
        // Assert
        Assertions.assertNotNull(commentService.findById(1));
    }

    @Test
    @DisplayName("Метод 'findAllCommentsByBookId()'. Комментария с ID '-999' нет у книги с ID '1'")
    void bookHasNotCommentTest() {
        // Act
        var commentsList = commentService.findAllCommentsByBookId(1);
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
        var commentsList = commentService.findAllCommentsByBookId(2);
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
        var initCountOfCommentsByBook = commentService.findAllCommentsByBookId(testBookId).size();
        // Act
        commentService.insert(testBookId, testCommentText);
        var resultCommentsList = commentService.findAllCommentsByBookId(testBookId);
        // Assert
        Assertions.assertTrue(initCountOfCommentsByBook < resultCommentsList.size());
        Assertions.assertTrue(
                resultCommentsList
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
        var testIdOfComment = 2;
        var existedComment = commentService.findById(testIdOfComment).orElse(new Comment());
        var initTextOfComment = existedComment.getText();
        // Act
        commentService.update(testIdOfComment, testCommentText);
        var resultComment = commentService.findById(testIdOfComment).orElse(new Comment());
        // Assert
        Assertions.assertNotEquals(initTextOfComment, resultComment.getText());
        Assertions.assertEquals(testCommentText, resultComment.getText());
    }

    @Test
    @DisplayName("Метод 'deleteById()'. Должен удалиться комментарий из БД")
    void deleteBookTest() {
        // Arrange
        var testBookId = 3;
        var commentsList = commentService.findAllCommentsByBookId(testBookId);
        var initCountComments = commentsList.size();
        var firstComment = commentsList.stream().findFirst().orElse(new Comment());
        // Act
        commentService.deleteById(firstComment.getId());
        // Assert
        Assertions.assertNotEquals(initCountComments, commentService.findAllCommentsByBookId(testBookId).size());
    }

}
