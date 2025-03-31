package ru.otus.hw.controllers.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw.dto.models.book.BookCreateDto;
import ru.otus.hw.dto.models.book.BookDto;
import ru.otus.hw.dto.models.book.BookUpdateDto;
import ru.otus.hw.services.BookService;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BooksRestController {

    private final BookService bookService;

    @GetMapping(path = "/")
    @ResponseBody
    public Flux<BookDto> getAllBooks() {
        return bookService.findAll();
    }

    @GetMapping(path = "/{book_id}")
    public Mono<BookDto> findBookById(@PathVariable("book_id") String bookId) {
        return bookService.findById(bookId);
    }

    @DeleteMapping(path = "/{book_id}")
    public Flux<BookDto> deleteBook(@PathVariable(value = "book_id") String bookId) {
        bookService.deleteById(bookId);
        return bookService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<BookDto> createBook(@Valid @RequestBody BookCreateDto createDto) {
        return bookService.create(createDto);
    }

    @PutMapping
    public Mono<BookDto> updateBook(@Valid @RequestBody BookUpdateDto updateDto) {
        return bookService.update(updateDto);
    }

}