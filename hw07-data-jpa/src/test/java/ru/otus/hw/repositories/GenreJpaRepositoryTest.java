package ru.otus.hw.repositories;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.otus.hw.models.Genre;

import java.util.Optional;

@DataJpaTest
public class GenreJpaRepositoryTest {

    @Autowired
    private GenreRepository genreRepository;

    @Test
    @DisplayName("Метод 'findById()'. В БД нет жанра с ID '-777'")
    void emptyWhenNotFoundGenreById() {
        // Assert
        Assertions.assertEquals(Optional.empty(), genreRepository.findById(-777L));
    }

    @Test
    @DisplayName("Метод 'findById()'. В БД есть жанр с ID '1'")
    void hasGenreById() {
        // Assert
        Assertions.assertNotNull(genreRepository.findById(1L));
    }

    @Test
    @DisplayName("Метод 'findAll()'. В БД нет жанра с ID '-888'")
    void inAllGenresHasNotGenre() {
        // Arrange
        var expectedGenre = new Genre(-888, "Genre_888");
        // Act
        var genresList = genreRepository.findAll();
        // Assert
        Assertions.assertFalse(genresList.contains(expectedGenre));
    }

    @Test
    @DisplayName("Метод 'findAll()'. В БД есть жанр с ID '2'")
    void inAllGenresHasGenre() {
        // Arrange
        var expectedGenre = new Genre(2, "Genre_2");
        // Act
        var genresList = genreRepository.findAll();
        // Assert
        Assertions.assertTrue(genresList.contains(expectedGenre));
    }

}
