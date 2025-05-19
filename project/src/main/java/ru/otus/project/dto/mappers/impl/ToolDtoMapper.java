package ru.otus.project.dto.mappers.impl;

import org.springframework.stereotype.Component;
import ru.otus.project.dto.mappers.DtoMapper;
import ru.otus.project.dto.models.tool.ToolDto;
import ru.otus.project.models.Tool;

@Component
public class ToolDtoMapper implements DtoMapper<ToolDto, Tool> {

    @Override
    public ToolDto toDto(Tool tool) {
        if (tool == null) {
            return null;
        }
        return ToolDto.builder()
                .id(tool.getId())
                .type(tool.getType())
                .diameter(tool.getDiameter())
                .length(tool.getLength())
                .manufacturer(tool.getManufacturer())
                .build();
    }

    @Override
    public Tool toModel(ToolDto dto) {
        if (dto == null) {
            return null;
        }
        Tool tool = new Tool();
        tool.setId(dto.getId());
        tool.setType(dto.getType());
        tool.setDiameter(dto.getDiameter());
        tool.setLength(dto.getLength());
        tool.setManufacturer(dto.getManufacturer());
        return tool;
    }

}
