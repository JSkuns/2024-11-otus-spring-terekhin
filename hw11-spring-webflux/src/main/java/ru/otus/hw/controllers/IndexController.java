package ru.otus.hw.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Mono;

@Controller
@RequiredArgsConstructor
public class IndexController {

    @GetMapping(path = "/")
    public Mono<String> index() {
        return Mono.just("index");
    }

}
