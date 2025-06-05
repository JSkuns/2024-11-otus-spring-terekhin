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
import ru.otus.hw.dto.models.book.BookDto;
import ru.otus.hw.dto.models.comment.CommentCreateDto;
import ru.otus.hw.dto.models.comment.CommentDto;
import ru.otus.hw.dto.models.comment.CommentUpdateDto;
import ru.otus.hw.services.CommentService;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureWebTestClient
@EnableMongock
class CommentsRestControllerTest {

    private final CommentDto commentDto1 = new CommentDto("id1", new BookDto(), "content1");
    private final CommentDto commentDto2 = new CommentDto("id2", new BookDto(), "content2");

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private CommentService commentService;

    @BeforeEach
    void setup() {
        when(commentService.findAllCommentsByBookId(any())).thenReturn(Flux.fromIterable(Arrays.asList(commentDto1, commentDto2)));
        when(commentService.findById(any())).thenReturn(Mono.just(commentDto1));
        when(commentService.create(any())).thenReturn(Mono.just(commentDto1));
        when(commentService.update(any())).thenReturn(Mono.just(commentDto1));
        when(commentService.deleteById(any())).thenReturn(Mono.empty());
    }

    @Test
    void findAllCommentsByBookId() {
        webTestClient.get().uri("/comments/book/123")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(CommentDto.class);
    }

    @Test
    void findCommentById() {
        webTestClient.get().uri("/comments/comment/id1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(CommentDto.class);
    }

    @Test
    void createComment() {
        CommentCreateDto createDto = new CommentCreateDto("author1", "content1");
        webTestClient.post().uri("/comments")
                .bodyValue(createDto)
                .exchange()
                .expectStatus().isOk()
                .expectBody(CommentDto.class);
    }

    @Test
    void updateComment() {
        CommentUpdateDto updateDto = new CommentUpdateDto("id1", "updatedContent");
        webTestClient.put().uri("/comments")
                .bodyValue(updateDto)
                .exchange()
                .expectStatus().isOk()
                .expectBody(CommentDto.class);
    }

    @Test
    void deleteComment() {
        webTestClient.delete().uri("/comments/id1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Void.class);
    }

}
