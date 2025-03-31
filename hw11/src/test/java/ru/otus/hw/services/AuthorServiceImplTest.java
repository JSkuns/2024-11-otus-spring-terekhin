package ru.otus.hw.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.hw.dto.mappers.impl.AuthorDtoMapper;
import ru.otus.hw.dto.models.author.AuthorDto;
import ru.otus.hw.services.impl.AuthorServiceImpl;

@DataJpaTest
@Import({AuthorServiceImpl.class, AuthorDtoMapper.class})
public class AuthorServiceImplTest {

//    @Autowired
//    private AuthorService authorService;
//
//    @Test
//    @DisplayName("Метод findAll(). В БД нет автора с ID '-123'")
//    void inAllAuthorsHasNotAuthorTest() {
//        // Act
//        var authorsList = authorService.findAll();
//        // Assert
//        Assertions.assertFalse(
//                authorsList
//                        .stream()
//                        .map(AuthorDto::getId)
//                        .anyMatch(elt -> elt == -123)
//        );
//    }
//
//    @Test
//    @DisplayName("Метод 'findAll()'. В БД есть автор с ID '2'")
//    void inAllAuthorsHasAuthorTest() {
//        // Act
//        var authorsList = authorService.findAll();
//        // Assert
//        Assertions.assertTrue(
//                authorsList
//                        .stream()
//                        .map(AuthorDto::getId)
//                        .anyMatch(elt -> elt == 2)
//        );
//    }

}
