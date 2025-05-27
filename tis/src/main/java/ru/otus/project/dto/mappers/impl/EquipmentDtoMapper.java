package ru.otus.project.dto.mappers.impl;

import org.springframework.stereotype.Component;
import ru.otus.project.dto.mappers.DtoMapper;
import ru.otus.project.dto.models.EquipmentDto;
import ru.otus.project.models.Equipment;

@Component
public class EquipmentDtoMapper implements DtoMapper<EquipmentDto, Equipment> {

    @Override
    public EquipmentDto toDto(Equipment equipment) {
        return EquipmentDto.builder()
                .id(equipment.getId())
                .type(equipment.getType()) // Тип станка
                .model(equipment.getModel()) // Модель станка
                .build();
    }

    @Override
    public Equipment toModel(EquipmentDto dto) {
        Equipment equipment = new Equipment();
        equipment.setId(dto.getId());
        equipment.setType(dto.getType()); // Устанавливаем тип станка
        equipment.setModel(dto.getModel()); // Устанавливаем модель станка
        return equipment;
    }

}