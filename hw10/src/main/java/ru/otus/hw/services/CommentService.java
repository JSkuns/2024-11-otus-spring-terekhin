package ru.otus.hw.services;

import ru.otus.hw.dto.models.comment.CommentCreateDto;
import ru.otus.hw.dto.models.comment.CommentDto;
import ru.otus.hw.dto.models.comment.CommentUpdateDto;

import java.util.List;

public interface CommentService {

    List<CommentDto> findAllCommentsByBookId(long id);

    CommentDto findById(long id);

    CommentDto create(CommentCreateDto commentCreateDto);

    CommentDto update(CommentUpdateDto commentUpdateDto);

    void deleteById(long id);

}
