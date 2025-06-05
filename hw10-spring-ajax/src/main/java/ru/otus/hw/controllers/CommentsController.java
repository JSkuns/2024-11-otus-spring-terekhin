package ru.otus.hw.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * <a href="http://localhost:8080/comments">...</a>
 */
@Controller
@RequiredArgsConstructor
public class CommentsController {

    @GetMapping(path = "/comments")
    public String index() {
        return "comments";
    }

}
