package ru.otus.project.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class GenresController {

    @GetMapping(path = "/genres")
    public String index() {
        return "genres";
    }

}
