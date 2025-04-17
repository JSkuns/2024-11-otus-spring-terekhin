package ru.otus.hw.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.hw.models.h2.Genre;
import ru.otus.hw.services.impl.GenreServiceImpl;

@DataJpaTest
@Import(GenreServiceImpl.class)
public class GenreServiceImplTest {

    @Autowired
    private GenreService genreService;

    @Test
    @DisplayName("Метод findAll(). В БД нет жанра с ID '-321'")
    void inAllGenresHasNotGenreTest() {
        // Act
        var genresList = genreService.findAll();
        // Assert
        Assertions.assertFalse(
                genresList
                        .stream()
                        .map(Genre::getId)
                        .anyMatch(elt -> elt == -321)
        );
    }

    @Test
    @DisplayName("Метод 'findAll()'. В БД есть жанр с ID '3'")
    void inAllGenresHasGenreTest() {
        // Act
        var genresList = genreService.findAll();
        // Assert
        Assertions.assertTrue(
                genresList
                        .stream()
                        .map(Genre::getId)
                        .anyMatch(elt -> elt == 3)
        );
    }

}
