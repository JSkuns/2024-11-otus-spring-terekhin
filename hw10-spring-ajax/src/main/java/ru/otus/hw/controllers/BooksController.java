package ru.otus.hw.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * <a href="http://localhost:8080/books">...</a>
 */
@Controller
@RequiredArgsConstructor
public class BooksController {

    @GetMapping(path = "/books")
    public String index() {
        return "books";
    }

}
