package ru.otus.hw.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * <a href="http://localhost:8080/genres">...</a>
 */
@Controller
@RequiredArgsConstructor
public class GenresController {

    @GetMapping(path = "/genres")
    public String index() {
        return "genres";
    }

}
