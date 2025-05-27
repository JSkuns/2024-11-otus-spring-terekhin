package ru.otus.project.services;

import ru.otus.project.dto.models.ThreadDto;

import java.util.List;

public interface ThreadsService {

    List<ThreadDto> findAll();

}