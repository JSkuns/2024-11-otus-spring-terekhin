package ru.otus.hw.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    @Transactional(readOnly = true)
    public List<CommentDto> findAllCommentsByBookId(long id) {
        var commentList = commentRepository.findByBookId(id);
        return commentDtoMapper.toDto(commentList);
    }

    @Override
    @Transactional(readOnly = true)
    public CommentDto findById(long id) {
        var comment = commentRepository.findById(id).orElse(null);
        return commentDtoMapper.toDto(Objects.requireNonNull(comment));
    }

    @Override
    @Transactional
    public CommentDto create(CommentCreateDto commentCreateDto) {
        var bookId = commentCreateDto.getBookId();
        var book = bookRepository.findById(bookId)
                .orElseThrow(() -> {
                    var errMsg = "Book with id %d not found".formatted(bookId);
                    log.error(errMsg);
                    return new NotFoundException(errMsg);
                });
        var comment = commentRepository.save(new Comment(0, commentCreateDto.getText(), book));
        return commentDtoMapper.toDto(comment);
    }

    @Override
    @Transactional
    public CommentDto update(CommentUpdateDto commentUpdateDto) {
        var id = commentUpdateDto.getId();
        var comment = commentRepository.findById(id)
                .orElseThrow(() -> {
                    var errMsg = "Comment with id %d not found".formatted(id);
                    log.error(errMsg);
                    return new NotFoundException(errMsg);
                });
        comment.setText(commentUpdateDto.getText());
        var savedComment = commentRepository.save(comment);
        commentRepository.flush();
        log.info("The comment with id %d has been changed".formatted(id));
        return commentDtoMapper.toDto(savedComment);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        commentRepository.deleteById(id);
        log.info("Comment with id %d was deleted".formatted(id));
    }

}