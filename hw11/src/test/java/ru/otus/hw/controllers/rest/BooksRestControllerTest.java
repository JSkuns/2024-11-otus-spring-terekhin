package ru.otus.hw.controllers.rest;

import com.github.cloudyrock.spring.v5.EnableMongock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw.dto.models.author.AuthorDto;
import ru.otus.hw.dto.models.book.BookCreateDto;
import ru.otus.hw.dto.models.book.BookDto;
import ru.otus.hw.dto.models.book.BookUpdateDto;
import ru.otus.hw.dto.models.genre.GenreDto;
import ru.otus.hw.services.BookService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureWebTestClient
@EnableMongock
class BooksRestControllerTest {

    private final BookDto bookDto1 = new BookDto("id1", "title1", new AuthorDto(), new GenreDto());
    private final BookDto bookDto2 = new BookDto("id2", "title2", new AuthorDto(), new GenreDto());

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private BookService bookService;

    @BeforeEach
    void setup() {
        when(bookService.findAll()).thenReturn(Flux.fromIterable(List.of(bookDto1, bookDto2)));
        when(bookService.findById(any())).thenReturn(Mono.just(bookDto1));
        when(bookService.create(any())).thenReturn(Mono.just(bookDto1));
        when(bookService.update(any())).thenReturn(Mono.just(bookDto1));
        when(bookService.deleteById(any())).thenReturn(Mono.empty());
    }

    @Test
    void testFindAllBooks() {
        webTestClient.get().uri("/books/")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(BookDto.class);
    }

    @Test
    void testFindBookById() {
        webTestClient.get().uri("/books/" + bookDto1.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(BookDto.class);
    }

    @Test
    void testDeleteBook() {
        webTestClient.delete().uri("/books/" + bookDto1.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(BookDto.class)
                .consumeWith(response -> {
                    List<BookDto> books = response.getResponseBody();
                    assertThat(books).doesNotContain(bookDto1);
                });
    }

    @Test
    void testCreateBook() {
        BookCreateDto createDto = new BookCreateDto("newTitle", null, null);
        webTestClient.post().uri("/books")
                .bodyValue(createDto)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(BookDto.class);
    }

    @Test
    void testUpdateBook() {
        BookUpdateDto updateDto = new BookUpdateDto(bookDto1.getId(), "updatedTitle", null, null);
        webTestClient.put().uri("/books")
                .bodyValue(updateDto)
                .exchange()
                .expectStatus().isOk()
                .expectBody(BookDto.class);
    }

}