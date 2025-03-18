package ru.otus.hw.controllers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import ru.otus.hw.dto.models.author.AuthorDto;
import ru.otus.hw.dto.models.book.BookCreateDto;
import ru.otus.hw.dto.models.book.BookDto;
import ru.otus.hw.dto.models.book.BookUpdateDto;
import ru.otus.hw.dto.models.genre.GenreDto;
import ru.otus.hw.services.BookService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = BooksController.class)
public class BooksControllerTest {

    @MockBean
    private BookService booksService;

    @Autowired
    private MockMvc mvc;

    @Test
    void shouldInsertNewBook() throws Exception {
        BookCreateDto bookCreateDto = new BookCreateDto("test", 1, 2);
        mvc.perform(post("/books/create").flashAttr("book_create_obj", bookCreateDto))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/books"));
        verify(booksService).create(bookCreateDto);
    }

    @Test
    void shouldUpdateBook() throws Exception {
        BookUpdateDto bookUpdateDto = new BookUpdateDto(1, "test", 2, 2);
        mvc.perform(post("/books/update").flashAttr("book_update_obj", bookUpdateDto))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/books"));
        verify(booksService).update(bookUpdateDto);
    }

    @Test
    void shouldThrownInternalServerError500() throws Exception {
        var result = mvc.perform(post("/booksss/update?err"));
        result
                .andExpect(status().isOk())
                .andExpect(view().name("error"));
        Assertions.assertInstanceOf(NoResourceFoundException.class, result.andReturn().getResolvedException());
    }

    @Test
    void shouldDeleteBook() throws Exception {
        mvc.perform(post("/books/delete?book_id=%d"
                        .formatted(1)))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/books"));
        verify(booksService).deleteById(any(Long.class));
    }

    private List<GenreDto> getExpectedGenres() {
        List<GenreDto> genres = new ArrayList<>();
        genres.add(GenreDto.builder().id(1).name("Genre_1").build());
        genres.add(GenreDto.builder().id(2).name("Genre_2").build());
        genres.add(GenreDto.builder().id(3).name("Genre_3").build());
        return genres;
    }

    private List<AuthorDto> getExpectedAuthors() {
        List<AuthorDto> authors = new ArrayList<>();
        authors.add(AuthorDto.builder().id(1).fullName("Author_1").build());
        authors.add(AuthorDto.builder().id(2).fullName("Author_2").build());
        authors.add(AuthorDto.builder().id(3).fullName("Author_3").build());
        return authors;
    }

    private List<BookDto> getExpectedBooks() {
        List<AuthorDto> authors = getExpectedAuthors();
        List<GenreDto> genres = getExpectedGenres();
        List<BookDto> books = new ArrayList<>();
        books.add(BookDto.builder().id(1).title("BookTitle_1").author(authors.get(0)).genre(genres.get(0)).build());
        books.add(BookDto.builder().id(2).title("BookTitle_2").author(authors.get(1)).genre(genres.get(1)).build());
        books.add(BookDto.builder().id(3).title("BookTitle_3").author(authors.get(2)).genre(genres.get(2)).build());
        return books;
    }

}
