package ru.otus.hw.services;

import reactor.core.publisher.Flux;
import ru.otus.hw.dto.models.author.AuthorDto;

public interface AuthorService {

    Flux<AuthorDto> findAll();

}
