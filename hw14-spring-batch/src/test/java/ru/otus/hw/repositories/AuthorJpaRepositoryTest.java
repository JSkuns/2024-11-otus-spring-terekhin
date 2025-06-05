package ru.otus.hw.repositories;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.otus.hw.models.h2.Author;
import ru.otus.hw.repositories.h2.AuthorRepository;

import java.util.Optional;

@DataJpaTest
public class AuthorJpaRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    @DisplayName("Метод 'findById()'. В БД нет автора с ID '-777'")
    void emptyWhenNotFoundAuthorById() {
        // Assert
        Assertions.assertEquals(Optional.empty(), authorRepository.findById(-777L));
    }

    @Test
    @DisplayName("Метод 'findById()'. В БД есть автор с ID '1'")
    void hasAuthorById() {
        // Assert
        Assertions.assertNotNull(authorRepository.findById(1L));
    }

    @Test
    @DisplayName("Метод 'findAll()'. В БД нет автора с ID '-888'")
    void inAllAuthorsHasNotAuthor() {
        // Arrange
        var expectedAuthor = new Author(-888, "Author_888");
        // Act
        var authorsList = authorRepository.findAll();
        // Assert
        Assertions.assertFalse(authorsList.contains(expectedAuthor));
    }

    @Test
    @DisplayName("Метод 'findAll()'. В БД есть автор с ID '2'")
    void inAllAuthorsHasAuthor() {
        // Arrange
        var expectedAuthor = new Author(2, "Author_2");
        // Act
        var authorsList = authorRepository.findAll();
        // Assert
        Assertions.assertTrue(authorsList.contains(expectedAuthor));
    }

}
