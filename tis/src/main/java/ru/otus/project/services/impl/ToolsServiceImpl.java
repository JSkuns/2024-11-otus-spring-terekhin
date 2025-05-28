package ru.otus.project.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.project.dto.mappers.impl.ToolDtoMapper;
import ru.otus.project.dto.models.ToolDto;
import ru.otus.project.exceptions.NotFoundException;
import ru.otus.project.models.Tool;
import ru.otus.project.repositories.ToolRepository;
import ru.otus.project.services.ToolsService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ToolsServiceImpl implements ToolsService {

    private final ToolRepository toolRepository;

    private final ToolDtoMapper toolDtoMapper;

    @Override
    @Transactional(readOnly = true)
    public List<ToolDto> findAll() {
        return toolRepository.findAll()
                .stream()
                .map(toolDtoMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ToolDto findById(Long id) {
        Optional<Tool> toolOptional = toolRepository.findById(id);
        return toolOptional.map(toolDtoMapper::toDto).orElseThrow(() -> {
            var errMsg = "Tool with id %s not found".formatted(id);
            log.error(errMsg);
            return new NotFoundException(errMsg);
        });
    }

    public ToolDto createUpdate(ToolDto toolDto) {
        var tool = toolRepository.findById(toolDto.getId())
                .orElseThrow(() -> {
                    var errMsg = "Tool with id %s not found".formatted(toolDto.getId());
                    log.error(errMsg);
                    return new NotFoundException(errMsg);
                });
        tool = toolDtoMapper.toModel(toolDto);
        Tool savedTool = toolRepository.save(tool);
        return toolDtoMapper.toDto(savedTool);
    }

    @Override
    public void deleteById(Long id) {
        toolRepository.deleteById(id);
        log.info("Tool with id %d was deleted".formatted(id));
    }

}
