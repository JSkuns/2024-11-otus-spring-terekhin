package ru.otus.hw.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Mono;

/**
 * <a href="http://localhost:8080/authors">...</a>
 */
@Controller
@RequiredArgsConstructor
public class AuthorsController {

    @GetMapping(path = "/authors")
    public Mono<String> index() {
        return Mono.just("authors");
    }

}
