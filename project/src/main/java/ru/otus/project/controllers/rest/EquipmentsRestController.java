package ru.otus.project.controllers.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.project.dto.models.EquipmentDto;
import ru.otus.project.services.EquipmentsService;

import java.util.List;

@RestController
@RequestMapping("/equipments")
@RequiredArgsConstructor
public class EquipmentsRestController {

    private final EquipmentsService equipmentsService;

    @GetMapping(path = "/")
    @ResponseBody
    public List<EquipmentDto> getAllEquipments() {
        return equipmentsService.findAll();
    }

}
