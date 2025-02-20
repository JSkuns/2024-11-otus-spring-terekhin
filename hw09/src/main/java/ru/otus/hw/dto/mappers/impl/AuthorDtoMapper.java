package ru.otus.hw.dto.mappers.impl;

import org.springframework.stereotype.Component;
import ru.otus.hw.dto.mappers.DtoMapper;
import ru.otus.hw.dto.models.author.AuthorDto;
import ru.otus.hw.models.Author;

import java.util.ArrayList;
import java.util.List;

@Component
public class AuthorDtoMapper implements DtoMapper<AuthorDto, Author> {

    @Override
    public AuthorDto toDto(Author source) {
        AuthorDto dto = new AuthorDto();
        dto.setId(source.getId());
        dto.setFullName(source.getFullName());
        return dto;
    }

    public List<AuthorDto> toDto(List<Author> authorList) {
        List<AuthorDto> authorDtoList = new ArrayList<>(authorList.size());
        authorList.forEach(author -> authorDtoList.add(toDto(author)));
        return authorDtoList;
    }

    @Override
    public Author toModel(AuthorDto source) {
        return null;
    }

}
