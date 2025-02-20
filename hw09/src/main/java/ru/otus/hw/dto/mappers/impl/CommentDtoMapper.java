package ru.otus.hw.dto.mappers.impl;

import ru.otus.hw.dto.mappers.DtoMapper;
import ru.otus.hw.dto.models.comment.CommentDto;
import ru.otus.hw.models.Comment;

public class CommentDtoMapper implements DtoMapper<CommentDto, Comment> {

    @Override
    public CommentDto toDto(Comment source) {
        return null;
    }

    @Override
    public Comment toModel(CommentDto source) {
        return null;
    }

}
