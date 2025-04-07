package ru.otus.hw.dto.mappers.impl;

import org.springframework.stereotype.Component;
import ru.otus.hw.dto.mappers.DtoMapper;
import ru.otus.hw.dto.models.author.AuthorDto;
import ru.otus.hw.models.Author;

@Component
public class AuthorDtoMapper implements DtoMapper<AuthorDto, Author> {

    @Override
    public AuthorDto toDto(Author source) {
        return new AuthorDto(source.getId(), source.getFullName());
    }

    @Override
    public Author toModel(AuthorDto source) {
        return null;
    }

}
