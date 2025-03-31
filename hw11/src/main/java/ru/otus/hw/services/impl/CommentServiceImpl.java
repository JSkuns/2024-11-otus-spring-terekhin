package ru.otus.hw.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw.dto.mappers.impl.CommentDtoMapper;
import ru.otus.hw.dto.models.comment.CommentCreateDto;
import ru.otus.hw.dto.models.comment.CommentDto;
import ru.otus.hw.dto.models.comment.CommentUpdateDto;
import ru.otus.hw.exceptions.NotFoundException;
import ru.otus.hw.models.Comment;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.CommentRepository;
import ru.otus.hw.services.CommentService;

import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final BookRepository bookRepository;

    private final CommentDtoMapper commentDtoMapper;

    @Override
    public Flux<CommentDto> findAllCommentsByBookId(String id) {
        return commentRepository.findByBookId(id).map(commentDtoMapper::toDto);
    }

    @Override
    public Mono<CommentDto> findById(String id) {
        return commentRepository.findById(id).map(commentDtoMapper::toDto);
    }

    @Override
    public Mono<CommentDto> create(CommentCreateDto commentCreateDto) {
        var bookId = commentCreateDto.getBookId();

        // Получаем книгу реактивно
        return bookRepository.findById(bookId)
                .switchIfEmpty(Mono.error(new NotFoundException("Книга с id %d не найдена".formatted(bookId))))
                .flatMap(book -> {
                    // Создаем новый комментарий
                    var comment = new Comment("0", commentCreateDto.getText(), book);
                    // Сохраняем комментарий реактивно
                    return commentRepository.save(comment);
                })
                .map(commentDtoMapper::toDto); // Преобразуем результат в DTO
    }

    @Override
    public Mono<CommentDto> update(CommentUpdateDto commentUpdateDto) {
        String id = commentUpdateDto.getId();
        return commentRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Comment with id %d not found".formatted(id))))
                .doOnNext(comment -> {
                    comment.setText(commentUpdateDto.getText());
                    log.info("The comment with id {} has been updated", id);
                })
                .flatMap(commentRepository::save)
                .map(commentDtoMapper::toDto);
    }

    @Override
    public Mono<Void> deleteById(String id) {
        var retVal = commentRepository.deleteById(id);
        log.info("Comment with id %d was deleted".formatted(id));
        return retVal;
    }

}