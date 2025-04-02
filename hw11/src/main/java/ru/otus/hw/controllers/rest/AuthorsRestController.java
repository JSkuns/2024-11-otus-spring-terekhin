package ru.otus.hw.controllers.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ru.otus.hw.dto.models.author.AuthorDto;
import ru.otus.hw.services.AuthorService;

@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor
public class AuthorsRestController {

    private final AuthorService authorService;

    @GetMapping(path = "/")
    @ResponseBody
    public Flux<AuthorDto> getAllAuthors() {
        return authorService.findAll();
    }

}
