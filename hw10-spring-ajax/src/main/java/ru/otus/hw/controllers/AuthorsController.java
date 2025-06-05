package ru.otus.hw.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * <a href="http://localhost:8080/authors">...</a>
 */
@Controller
@RequiredArgsConstructor
public class AuthorsController {

    @GetMapping(path = "/authors")
    public String index() {
        return "authors";
    }

}
