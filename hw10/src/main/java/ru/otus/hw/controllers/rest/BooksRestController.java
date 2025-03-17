package ru.otus.hw.controllers.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.hw.dto.models.book.BookDto;
import ru.otus.hw.services.BookService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BooksRestController {

    private final BookService bookService;

    @GetMapping(path = "/books/all")
    @ResponseBody
    public List<BookDto> getAllBooks() {
        return bookService.findAll();
    }

    @GetMapping(path = "/books/find")
    @ResponseBody
    public List<BookDto> findBookById(@RequestParam(value = "book_id") String id) {
        var book = bookService.findById(Long.parseLong(id));
        return List.of(book);
    }

}
