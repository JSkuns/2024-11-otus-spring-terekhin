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
import ru.otus.hw.models.Genre;
import ru.otus.hw.services.GenreService;

@DataMongoTest
@Import(GenreServiceImpl.class)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
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
                        .anyMatch(elt -> elt.equals("-321"))
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
                        .anyMatch(elt -> elt.equals("3"))
        );
    }

}
