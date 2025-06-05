package ru.otus.hw.controllers.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.hw.dto.models.comment.CommentCreateDto;
import ru.otus.hw.dto.models.comment.CommentDto;
import ru.otus.hw.dto.models.comment.CommentUpdateDto;
import ru.otus.hw.services.CommentService;

import java.util.List;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentsRestController {

    private final CommentService commentService;

    @GetMapping(path = "/book/{book_id}")
    public List<CommentDto> findCommentByBookId(@PathVariable("book_id") Long bookId) {
        return commentService.findAllCommentsByBookId(bookId);
    }

    @GetMapping(path = "/comment/{comment_id}")
    public CommentDto findCommentById(@PathVariable("comment_id") Long commentId) {
        return commentService.findById(commentId);
    }

    @PostMapping
    public CommentDto createComment(@Valid @RequestBody CommentCreateDto createDto) {
        return commentService.create(createDto);
    }

    @PutMapping
    public CommentDto updateComment(@Valid @RequestBody CommentUpdateDto updateDto) {
        return commentService.update(updateDto);
    }

    @DeleteMapping(path = "/{comment_id}")
    public void deleteBook(@PathVariable(value = "comment_id") Long commentId) {
        commentService.deleteById(commentId);
    }

}
