package ru.otus.hw.services;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw.dto.models.comment.CommentCreateDto;
import ru.otus.hw.dto.models.comment.CommentDto;
import ru.otus.hw.dto.models.comment.CommentUpdateDto;

public interface CommentService {

    Flux<CommentDto> findAllCommentsByBookId(String id);

    Mono<CommentDto> findById(String id);

    Mono<CommentDto> create(CommentCreateDto commentCreateDto);

    Mono<CommentDto> update(CommentUpdateDto commentUpdateDto);

    Mono<Void> deleteById(String id);

}
