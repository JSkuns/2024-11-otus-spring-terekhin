package ru.otus.hw.dto.mappers.impl;

import org.springframework.stereotype.Component;
import ru.otus.hw.dto.mappers.DtoMapper;
import ru.otus.hw.dto.models.genre.GenreDto;
import ru.otus.hw.models.Genre;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GenreDtoMapper implements DtoMapper<GenreDto, Genre> {

    @Override
    public GenreDto toDto(Genre source) {
        return new GenreDto(source.getId(), source.getName());
    }

    public List<GenreDto> toDto(List<Genre> genreList) {
        return genreList
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Genre toModel(GenreDto source) {
        return null;
    }

}
