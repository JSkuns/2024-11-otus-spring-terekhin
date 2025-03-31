package ru.otus.hw.controllers.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw.dto.models.comment.CommentCreateDto;
import ru.otus.hw.dto.models.comment.CommentDto;
import ru.otus.hw.dto.models.comment.CommentUpdateDto;
import ru.otus.hw.services.CommentService;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentsRestController {

    private final CommentService commentService;

    @GetMapping(path = "/book/{book_id}")
    public Flux<CommentDto> findCommentByBookId(@PathVariable("book_id") String bookId) {
        return commentService.findAllCommentsByBookId(bookId);
    }

    @GetMapping(path = "/comment/{comment_id}")
    public Mono<CommentDto> findCommentById(@PathVariable("comment_id") String commentId) {
        return commentService.findById(commentId);
    }

    @PostMapping
    public Mono<CommentDto> createComment(@Valid @RequestBody CommentCreateDto createDto) {
        return commentService.create(createDto);
    }

    @PutMapping
    public Mono<CommentDto> updateComment(@Valid @RequestBody CommentUpdateDto updateDto) {
        return commentService.update(updateDto);
    }

    @DeleteMapping(path = "/{comment_id}")
    public Mono<Void> deleteBook(@PathVariable(value = "comment_id") String commentId) {
        return commentService.deleteById(commentId);
    }

}
