package ru.otus.hw.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.otus.hw.dto.models.author.AuthorDto;
import ru.otus.hw.services.AuthorService;

import java.util.List;

/**
 * <a href="http://localhost:8080/authors">...</a>
 */
@Controller
@RequiredArgsConstructor
public class AuthorsController {

    private final AuthorService authorService;

    @GetMapping(path = "/authors")
    public String index(Model model) {
        List<AuthorDto> authors = authorService.findAll();
        model.addAttribute("authors", authors);
        return "authors";
    }

}
