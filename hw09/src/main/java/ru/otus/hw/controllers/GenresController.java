package ru.otus.hw.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.otus.hw.models.Genre;
import ru.otus.hw.services.GenreService;

import java.util.List;

/**
 * <a href="http://localhost:8080/genres">...</a>
 */
@Controller
@RequiredArgsConstructor
public class GenresController {

    private final GenreService genreService;

    @GetMapping(path = "/genres")
    public String index(Model model) {
        List<Genre> genres = genreService.findAll();
        model.addAttribute("genres", genres);
        return "genres";
    }

}
