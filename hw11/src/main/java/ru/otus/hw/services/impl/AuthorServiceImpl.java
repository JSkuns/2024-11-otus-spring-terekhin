package ru.otus.hw.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import ru.otus.hw.dto.mappers.impl.AuthorDtoMapper;
import ru.otus.hw.dto.models.author.AuthorDto;
import ru.otus.hw.repositories.AuthorRepository;
import ru.otus.hw.services.AuthorService;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    private final AuthorDtoMapper authorDtoMapper;

    @Override
    @Transactional(readOnly = true)
    public Flux<AuthorDto> findAll() {
        return authorRepository.findAll().map(authorDtoMapper::toDto);
    }

}
