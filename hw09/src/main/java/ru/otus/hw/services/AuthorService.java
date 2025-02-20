package ru.otus.hw.services;

import ru.otus.hw.dto.models.author.AuthorDto;

import java.util.List;

public interface AuthorService {

    List<AuthorDto> findAll();

}
