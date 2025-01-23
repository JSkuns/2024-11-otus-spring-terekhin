package ru.otus.hw.repositories;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.Optional;

@DataJpaTest
@Import(JpaGenreRepository.class)
public class JpaGenreRepositoryTest {

    @Autowired
    private GenreRepository genreRepository;

    @Test
    @DisplayName("В БД нет записей с номером '-777'")
    void expectedEmptyWhenNotFound() {
        Assertions.assertEquals(Optional.empty(), genreRepository.findById(-777));
    }

    @Test
    @DisplayName("В БД есть запись с номером '1'")
    void expectedNotEmpty() {
        Assertions.assertNotNull(genreRepository.findById(1));
    }

}
