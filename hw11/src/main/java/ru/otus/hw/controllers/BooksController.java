package ru.otus.hw.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Mono;

/**
 * <a href="http://localhost:8080/books">...</a>
 */
@Controller
@RequiredArgsConstructor
public class BooksController {

    @GetMapping(path = "/books")
    public Mono<String> index() {
        return Mono.just("books");
    }

}
