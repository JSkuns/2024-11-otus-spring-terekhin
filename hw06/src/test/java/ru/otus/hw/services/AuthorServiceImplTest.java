package ru.otus.hw.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.hw.models.Author;
import ru.otus.hw.repositories.AuthorJpaRepository;

@DataJpaTest
@Import({AuthorServiceImpl.class, AuthorJpaRepository.class})
public class AuthorServiceImplTest {

    @Autowired
    private AuthorService authorService;

    @Test
    @DisplayName("Метод findAll(). В БД нет автора с ID '-123'")
    void inAllAuthorsHasNotAuthorTest() {
        // Arrange
        var expectedAuthor = new Author(-123, null);
        // Act
        var authorsList = authorService.findAll();
        // Assert
        Assertions.assertFalse(
                authorsList
                        .stream()
                        .map(Author::getId)
                        .anyMatch(elt -> elt == expectedAuthor.getId())
        );
    }

    @Test
    @DisplayName("Метод 'findAll()'. В БД есть автор с ID '2'")
    void inAllAuthorsHasAuthorTest() {
        // Arrange
        var expectedAuthor = new Author(2, null);
        // Act
        var authorsList = authorService.findAll();
        // Assert
        Assertions.assertTrue(
                authorsList
                        .stream()
                        .map(Author::getId)
                        .anyMatch(elt -> elt == expectedAuthor.getId())
        );
    }

}
