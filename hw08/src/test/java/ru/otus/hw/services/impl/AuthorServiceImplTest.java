package ru.otus.hw.services.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.models.Author;
import ru.otus.hw.services.AuthorService;

@DataMongoTest
@Import(AuthorServiceImpl.class)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AuthorServiceImplTest {

    @Autowired
    private AuthorService authorService;

    @Test
    @DisplayName("Метод findAll(). В БД нет автора с ID '-123'")
    void inAllAuthorsHasNotAuthorTest() {
        // Act
        var authorsList = authorService.findAll();
        // Assert
        Assertions.assertFalse(
                authorsList
                        .stream()
                        .map(Author::getId)
                        .anyMatch(elt -> elt == "-123")
        );
    }

    @Test
    @DisplayName("Метод 'findAll()'. В БД есть автор с ID '2'")
    void inAllAuthorsHasAuthorTest() {
        // Act
        var authorsList = authorService.findAll();
        // Assert
        Assertions.assertTrue(
                authorsList
                        .stream()
                        .map(Author::getId)
                        .anyMatch(elt -> elt.equals("2"))
        );
    }

}
