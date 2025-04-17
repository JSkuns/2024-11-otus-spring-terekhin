package ru.otus.hw.dto.mappers.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.dto.mappers.DtoMapper;
import ru.otus.hw.dto.models.comment.CommentDto;
import ru.otus.hw.models.Comment;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class CommentDtoMapper implements DtoMapper<CommentDto, Comment> {

    private final BookDtoMapper bookDtoMapper;

    @Override
    public CommentDto toDto(Comment source) {
        return new CommentDto(source.getId(), bookDtoMapper.toDto(source.getBook()), source.getText());
    }

    public List<CommentDto> toDto(List<Comment> commentList) {
        return commentList
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Comment toModel(CommentDto source) {
        return null;
    }

}
