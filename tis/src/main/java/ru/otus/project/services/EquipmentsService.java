package ru.otus.project.services;

import ru.otus.project.dto.models.EquipmentDto;

import java.util.List;

public interface EquipmentsService {

    List<EquipmentDto> findAll();

}