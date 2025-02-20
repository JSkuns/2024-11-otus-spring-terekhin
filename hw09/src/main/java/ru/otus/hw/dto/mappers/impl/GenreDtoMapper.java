package ru.otus.hw.dto.mappers.impl;

import org.springframework.stereotype.Component;
import ru.otus.hw.dto.mappers.DtoMapper;
import ru.otus.hw.dto.models.genre.GenreDto;
import ru.otus.hw.models.Genre;

import java.util.ArrayList;
import java.util.List;

@Component
public class GenreDtoMapper implements DtoMapper<GenreDto, Genre> {

    @Override
    public GenreDto toDto(Genre source) {
        GenreDto dto = new GenreDto();
        dto.setId(source.getId());
        dto.setName(source.getName());
        return dto;
    }

    public List<GenreDto> toDto(List<Genre> genreList) {
        List<GenreDto> genreDtoList = new ArrayList<>(genreList.size());
        genreList.forEach(genre -> genreDtoList.add(toDto(genre)));
        return genreDtoList;
    }

    @Override
    public Genre toModel(GenreDto source) {
        return null;
    }

}
