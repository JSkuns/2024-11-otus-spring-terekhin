package ru.otus.hw.controllers.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ru.otus.hw.dto.models.genre.GenreDto;
import ru.otus.hw.services.GenreService;

@RestController
@RequestMapping("/genres")
@RequiredArgsConstructor
public class GenresRestController {

    private final GenreService genreService;

    @GetMapping(path = "/")
    @ResponseBody
    public Flux<GenreDto> getAllGenres() {
        return genreService.findAll();
    }

}
