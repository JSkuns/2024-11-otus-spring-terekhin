package ru.otus.hw.controllers.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import ru.otus.hw.dto.models.author.AuthorDto;
import ru.otus.hw.services.AuthorService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class AuthorsRestControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private AuthorService authorService;

    private List<AuthorDto> authors;

    @BeforeEach
    void setup() {
        authors = List.of(
                new AuthorDto("authorId1", "First Author"),
                new AuthorDto("authorId2", "Second Author")
        );
    }

    @Test
    void shouldReturnAllAuthors() {
        // Given
        Flux<AuthorDto> mockData = Flux.fromIterable(authors);
        when(authorService.findAll()).thenReturn(mockData);

        // When
        Flux<AuthorDto> response = webTestClient.get()
                .uri("/authors/")
                .exchange()
                .expectStatus().isOk()
                .returnResult(AuthorDto.class)
                .getResponseBody();

        // Then
        StepVerifier.create(response)
                .consumeNextWith(actual -> {
                    assertNotNull(actual);
                    assertEquals("authorId1", actual.getId());
                    assertEquals("First Author", actual.getFullName());
                })
                .consumeNextWith(actual -> {
                    assertNotNull(actual);
                    assertEquals("authorId2", actual.getId());
                    assertEquals("Second Author", actual.getFullName());
                })
                .verifyComplete();
    }

    @Test
    void shouldReturnEmptyListWhenNoAuthors() {
        // Given
        Flux<AuthorDto> mockData = Flux.empty();
        when(authorService.findAll()).thenReturn(mockData);

        // When
        Flux<AuthorDto> response = webTestClient.get()
                .uri("/authors/")
                .exchange()
                .expectStatus().isOk()
                .returnResult(AuthorDto.class)
                .getResponseBody();

        // Then
        StepVerifier.create(response)
                .verifyComplete();
    }

}