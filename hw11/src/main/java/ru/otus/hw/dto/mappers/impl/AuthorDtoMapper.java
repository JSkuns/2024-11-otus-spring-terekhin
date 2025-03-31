package ru.otus.hw.dto.mappers.impl;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import ru.otus.hw.dto.mappers.DtoMapper;
import ru.otus.hw.dto.models.author.AuthorDto;
import ru.otus.hw.models.Author;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AuthorDtoMapper implements DtoMapper<AuthorDto, Author> {

    @Override
    public AuthorDto toDto(Author source) {
        return new AuthorDto(source.getId(), source.getFullName());
    }

    public List<AuthorDto> toDto(List<Author> authorList) {
        return authorList
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Author toModel(AuthorDto source) {
        return null;
    }

}
