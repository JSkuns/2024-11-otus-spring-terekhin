package ru.otus.project.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.project.dto.mappers.impl.ThreadDtoMapper;
import ru.otus.project.dto.models.ThreadDto;
import ru.otus.project.repositories.ThreadRepository;
import ru.otus.project.services.ThreadsService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ThreadsServiceImpl implements ThreadsService {

    private final ThreadRepository threadRepository;

    private final ThreadDtoMapper threadDtoMapper;

    @Override
    @Transactional(readOnly = true)
    public List<ThreadDto> findAll() {
        return threadRepository.findAll()
                .stream()
                .map(threadDtoMapper::toDto)
                .collect(Collectors.toList());
    }

}