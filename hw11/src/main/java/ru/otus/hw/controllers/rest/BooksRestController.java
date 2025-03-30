package ru.otus.hw.controllers.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
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
    public BookDto findBookById(@PathVariable("book_id") Long bookId) {
        return bookService.findById(bookId);
    }

    @DeleteMapping(path = "/{book_id}")
    public List<BookDto> deleteBook(@PathVariable(value = "book_id") Long bookId) {
        bookService.deleteById(bookId);
        return bookService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
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
