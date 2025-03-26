package ru.otus.hw.controllers.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.hw.dto.models.book.BookDto;
import ru.otus.hw.services.BookService;

import java.util.List;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BooksRestController {

    private final BookService bookService;

    @GetMapping(path = "/all")
    @ResponseBody
    public List<BookDto> getAllBooks() {
        return bookService.findAll();
    }

    @GetMapping(path = "/{book_id}")
    public ResponseEntity<BookDto> findBookById(@PathVariable("book_id") Long book_id) {
        return ResponseEntity.ok(bookService.findById(book_id));
    }

}
