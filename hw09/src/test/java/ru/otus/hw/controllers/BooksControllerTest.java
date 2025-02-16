package ru.otus.hw.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;
import ru.otus.hw.services.AuthorService;
import ru.otus.hw.services.BookService;
import ru.otus.hw.services.GenreService;

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

    @MockBean
    private AuthorService authorsService;

    @MockBean
    private GenreService genresService;

    @Autowired
    private MockMvc mvc;

    @Test
    void shouldReturnAllBooks() throws Exception {
        List<Book> expectedBooks = getExpectedBooks();
        given(booksService.findAll()).willReturn(expectedBooks);
        mvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("books", expectedBooks));
    }

    @Test
    void shouldInsertNewBook() throws Exception {
        mvc.perform(post("/books/insert?title=%s&author_id=%d&genre_id=%d"
                        .formatted("test", 1, 2)))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/books"));
        verify(booksService).insert(any(String.class), any(Long.class), any(Long.class));
    }

    @Test
    void shouldUpdateBook() throws Exception {
        mvc.perform(post("/books/update?id=%d&title=%s&author_id=%d&genre_id=%d"
                        .formatted(1, "test", 2, 2)))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/books"));
        verify(booksService).update(any(Long.class), any(String.class), any(Long.class), any(Long.class));
    }

    @Test
    void shouldDeleteBook() throws Exception {
        mvc.perform(post("/books/delete?book_id=%d"
                        .formatted(1)))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/books"));
        verify(booksService).deleteById(any(Long.class));
    }

    @Test
    void shouldFoundBook() throws Exception {
        mvc.perform(get("/books/find?book_id=%d"
                        .formatted(1)))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/books"));
        verify(booksService).findById(any(Long.class));
    }

    private List<Genre> getExpectedGenres() {
        List<Genre> genres = new ArrayList<>();
        genres.add(Genre.builder().id(1).name("Genre_1").build());
        genres.add(Genre.builder().id(2).name("Genre_2").build());
        genres.add(Genre.builder().id(3).name("Genre_3").build());
        return genres;
    }

    private List<Author> getExpectedAuthors() {
        List<Author> authors = new ArrayList<>();
        authors.add(Author.builder().id(1).fullName("Author_1").build());
        authors.add(Author.builder().id(2).fullName("Author_2").build());
        authors.add(Author.builder().id(3).fullName("Author_3").build());
        return authors;
    }

    private List<Book> getExpectedBooks() {
        List<Author> authors = getExpectedAuthors();
        List<Genre> genres = getExpectedGenres();
        List<Book> books = new ArrayList<>();
        books.add(Book.builder().id(1).title("BookTitle_1").author(authors.get(0)).genre(genres.get(0)).build());
        books.add(Book.builder().id(2).title("BookTitle_2").author(authors.get(1)).genre(genres.get(1)).build());
        books.add(Book.builder().id(3).title("BookTitle_3").author(authors.get(2)).genre(genres.get(2)).build());
        return books;
    }

}
