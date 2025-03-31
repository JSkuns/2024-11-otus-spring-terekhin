package ru.otus.hw.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Mono;

/**
 * <a href="http://localhost:8080/comments">...</a>
 */
@Controller
@RequiredArgsConstructor
public class CommentsController {

    @GetMapping(path = "/comments")
    public Mono<String> index() {
        return Mono.just("comments");
    }

}
