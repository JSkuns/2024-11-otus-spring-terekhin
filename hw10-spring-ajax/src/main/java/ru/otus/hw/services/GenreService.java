package ru.otus.hw.services;

import ru.otus.hw.dto.models.genre.GenreDto;

import java.util.List;

public interface GenreService {

    List<GenreDto> findAll();

}
