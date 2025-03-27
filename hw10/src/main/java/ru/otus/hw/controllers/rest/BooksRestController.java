package ru.otus.hw.controllers.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.hw.dto.models.book.BookCreateDto;
import ru.otus.hw.dto.models.book.BookDto;
import ru.otus.hw.dto.models.book.BookUpdateDto;
import ru.otus.hw.services.BookService;

import java.util.List;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BooksRestController {

    private final BookService bookService;

    @GetMapping(path = "/")
    @ResponseBody
    public List<BookDto> getAllBooks() {
        return bookService.findAll();
    }

    @GetMapping(path = "/{book_id}")
    public ResponseEntity<BookDto> findBookById(@PathVariable("book_id") Long book_id) {
        return ResponseEntity.ok(bookService.findById(book_id));
    }

    @DeleteMapping(path = "/{book_id}")
    public List<BookDto> deleteBook(@PathVariable(value = "book_id") Long book_id) {
        bookService.deleteById(book_id);
        return bookService.findAll();
    }

    @PostMapping
    public List<BookDto> createBook(@Valid @RequestBody BookCreateDto createDto) {
        bookService.create(createDto);
        return bookService.findAll();
    }

    @PutMapping
    public List<BookDto> updateBook(@Valid @RequestBody BookUpdateDto updateDto) {
        bookService.update(updateDto);
        return bookService.findAll();
    }

}
