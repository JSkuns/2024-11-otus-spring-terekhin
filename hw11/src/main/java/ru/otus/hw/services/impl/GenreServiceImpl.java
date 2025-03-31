package ru.otus.hw.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import ru.otus.hw.dto.mappers.impl.GenreDtoMapper;
import ru.otus.hw.dto.models.genre.GenreDto;
import ru.otus.hw.repositories.GenreRepository;
import ru.otus.hw.services.GenreService;

@RequiredArgsConstructor
@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    private final GenreDtoMapper genreDtoMapper;

    @Override
    public Flux<GenreDto> findAll() {
        return genreRepository.findAll().map(genreDtoMapper::toDto);
    }

}
