package ru.otus.hw.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Book;
import ru.otus.hw.services.BookService;

import java.util.List;

/**
 * <a href="http://localhost:8080/books">...</a>
 */
@Controller
@RequiredArgsConstructor
public class BooksController {

    private final BookService bookService;

    @GetMapping(path = "/books")
    public String index(Model model) {
        List<Book> books = bookService.findAll();
        model.addAttribute("books", books);
        return "books";
    }

    @PostMapping(path = "/books/delete")
    public String deleteBook(@RequestParam(value = "book_id") String id) {
        var idLong = parseIdFromStringToLong(id);
        if (idLong == null) {
            return "redirect:/error";
        }
        bookService.deleteById(Long.parseLong(id));
        return "redirect:/books";
    }

    @PostMapping(path = "/books/insert")
    public String insertBook(
            @RequestParam(value = "title") String title,
            @RequestParam(value = "author_id") String authorId,
            @RequestParam(value = "genre_id") String genreId
    ) {
        var authorIdLong = parseIdFromStringToLong(authorId);
        var genreIdLong = parseIdFromStringToLong(genreId);
        if (authorIdLong == null || genreIdLong == null) {
            return "redirect:/error";
        }
        try {
            bookService.insert(title, authorIdLong, genreIdLong);
        } catch (EntityNotFoundException e) {
            return "redirect:/books";
        }
        return "redirect:/books";
    }

    @PostMapping(path = "/books/update")
    public String updateBook(
            @RequestParam(value = "id") String id,
            @RequestParam(value = "title") String title,
            @RequestParam(value = "author_id") String authorId,
            @RequestParam(value = "genre_id") String genreId
    ) {
        var idLong = parseIdFromStringToLong(id);
        var authorIdLong = parseIdFromStringToLong(authorId);
        var genreIdLong = parseIdFromStringToLong(genreId);
        if (idLong == null || authorIdLong == null || genreIdLong == null) {
            return "redirect:/error";
        }
        try {
            bookService.update(idLong, title, authorIdLong, genreIdLong);
        } catch (EntityNotFoundException ex) {
            return "redirect:/books";
        }
        return "redirect:/books";
    }

    @GetMapping(path = "/books/find")
    public String findBookById(
            @RequestParam(value = "book_id") String id,
            Model model
    ) {
        var idLong = parseIdFromStringToLong(id);
        if (idLong == null) {
            return "redirect:/error";
        }
        var book = bookService.findById(idLong).orElse(null);
        if (book == null) {
            return "redirect:/books";
        }
        List<Book> books = List.of(book);
        model.addAttribute("books", books);
        return "books";
    }

    private Long parseIdFromStringToLong(String val) {
        try {
            return Long.parseLong(val);
        } catch (NumberFormatException ex) {
            return null;
        }
    }

}
