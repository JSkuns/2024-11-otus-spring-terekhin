package ru.otus.hw.services;

import reactor.core.publisher.Flux;
import ru.otus.hw.dto.models.genre.GenreDto;

public interface GenreService {

    Flux<GenreDto> findAll();

}
