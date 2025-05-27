package ru.otus.project.controllers.mvc;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class EquipmentsController {

    @GetMapping(path = "/equipments")
    public String index() {
        return "equipments";
    }

}
