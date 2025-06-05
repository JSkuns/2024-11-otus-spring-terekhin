package ru.otus.project.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.project.dto.mappers.impl.EquipmentDtoMapper;
import ru.otus.project.dto.models.EquipmentDto;
import ru.otus.project.repositories.EquipmentRepository;
import ru.otus.project.services.EquipmentsService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class EquipmentsServiceImpl implements EquipmentsService {

    private final EquipmentRepository equipmentRepository;

    private final EquipmentDtoMapper equipmentDtoMapper;

    @Override
    @Transactional(readOnly = true)
    public List<EquipmentDto> findAll() {
        return equipmentRepository.findAll()
                .stream()
                .map(equipmentDtoMapper::toDto)
                .collect(Collectors.toList());
    }

}