package ru.otus.hw.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.dto.mappers.impl.AuthorDtoMapper;
import ru.otus.hw.dto.models.author.AuthorDto;
import ru.otus.hw.models.Author;
import ru.otus.hw.repositories.AuthorRepository;
import ru.otus.hw.services.AuthorService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    private final AuthorDtoMapper authorDtoMapper;

    @Override
    @Transactional(readOnly = true)
    public List<AuthorDto> findAll() {
        List<Author> authorList = authorRepository.findAll();
        return authorDtoMapper.toDto(authorList);
    }

}
