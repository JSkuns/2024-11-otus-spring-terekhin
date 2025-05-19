package ru.otus.project.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <a href="http://localhost:8080/books">...</a>
 */
@Controller
@RequiredArgsConstructor
public class BooksController {

    @GetMapping(path = "/books")
    public String index(Model model) {
        return "books";
    }

    @PostMapping(path = "/books/delete")
    public String deleteBook(@RequestParam(value = "book_id") String id) {
        return "redirect:/books";
    }

}
