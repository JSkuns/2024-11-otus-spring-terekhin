package ru.otus.hw.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.dto.mappers.impl.GenreDtoMapper;
import ru.otus.hw.dto.models.genre.GenreDto;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.GenreRepository;
import ru.otus.hw.services.GenreService;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    private final GenreDtoMapper genreDtoMapper;

    @Override
    @Transactional(readOnly = true)
    public List<GenreDto> findAll() {
        List<Genre> genreList = genreRepository.findAll();
        return genreDtoMapper.toDto(genreList);
    }

}
