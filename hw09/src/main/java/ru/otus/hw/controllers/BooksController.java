package ru.otus.hw.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.hw.dto.models.book.BookCreateDto;
import ru.otus.hw.dto.models.book.BookDto;
import ru.otus.hw.dto.models.book.BookUpdateDto;
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
        var bookDtoList = bookService.findAll();
        model.addAttribute("books", bookDtoList);
        addObjectsOnView(model);
        return "books";
    }

    @PostMapping(path = "/books/delete")
    public String deleteBook(@RequestParam(value = "book_id") String id) {
        bookService.deleteById(Long.parseLong(id));
        return "redirect:/books";
    }

    @PostMapping(path = "/books/create")
    public String createBook(@Valid @ModelAttribute(value = "book_create_obj") BookCreateDto createDto) {
        bookService.create(createDto);
        return "redirect:/books";
    }

    @PostMapping(path = "/books/update")
    public String updateBook(@Valid @ModelAttribute(value = "book_update_obj") BookUpdateDto updateDto) {
        bookService.update(updateDto);
        return "redirect:/books";
    }

    @GetMapping(path = "/books/find")
    public String findBookById(@RequestParam(value = "book_id") String id, Model model) {
        var book = bookService.findById(Long.parseLong(id));
        List<BookDto> bookDtoList = List.of(book);
        model.addAttribute("books", bookDtoList);
        addObjectsOnView(model);
        return "books";
    }

    private void addObjectsOnView(Model model) {
        model.addAttribute("book_create_obj", new BookCreateDto());
        model.addAttribute("book_update_obj", new BookUpdateDto());
    }

}
