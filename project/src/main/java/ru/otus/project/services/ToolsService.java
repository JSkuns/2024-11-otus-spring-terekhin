package ru.otus.project.services;

import ru.otus.project.dto.models.tool.ToolDto;

import java.util.List;

public interface ToolsService {

    List<ToolDto> findAll();

    ToolDto findById(Long id);

    ToolDto createUpdate(ToolDto toolDto);

    void deleteById(Long id);

}
