package ru.otus.hw.repositories;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.Optional;

@DataJpaTest
@Import(JpaCommentRepository.class)
public class JpaCommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Test
    @DisplayName("Метод 'findById()'. В БД нет комментария с ID '-777'")
    void emptyWhenNotFoundCommentById() {
        Assertions.assertEquals(Optional.empty(), commentRepository.findById(-777));
    }

    @Test
    @DisplayName("Метод 'findById()'. В БД есть комментарий с ID '1'")
    void hasCommentById() {
        Assertions.assertNotNull(commentRepository.findById(1));
    }

}
