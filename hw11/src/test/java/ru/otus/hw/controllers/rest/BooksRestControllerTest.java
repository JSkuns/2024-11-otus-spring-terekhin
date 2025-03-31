package ru.otus.hw.controllers.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.dto.models.author.AuthorDto;
import ru.otus.hw.dto.models.book.BookCreateDto;
import ru.otus.hw.dto.models.book.BookDto;
import ru.otus.hw.dto.models.book.BookUpdateDto;
import ru.otus.hw.dto.models.genre.GenreDto;
import ru.otus.hw.services.BookService;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BooksRestController.class)
@ActiveProfiles("test")
class BooksRestControllerTest {

//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private BookService bookService;
//
//    @Test
//    void testGetAllBooksSuccess() throws Exception {
//        List<BookDto> books = getExpectedBooks();
//
//        when(bookService.findAll()).thenReturn(books);
//
//        mockMvc.perform(get("/books/"))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(3)))
//                .andExpect(jsonPath("$[0].id").value(1))
//                .andExpect(jsonPath("$[0].title").value("BookTitle_1"))
//                .andExpect(jsonPath("$[1].id").value(2))
//                .andExpect(jsonPath("$[1].title").value("BookTitle_2"));
//    }
//
//    @Test
//    void testFindBookByIdSuccess() throws Exception {
//        BookDto expectedBook = getExpectedBooks().get(0);
//        var bookId = expectedBook.getId();
//
//        when(bookService.findById(bookId)).thenReturn(expectedBook);
//
//        mockMvc.perform(get("/books/{book_id}", bookId))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(bookId))
//                .andExpect(jsonPath("$.title").value("BookTitle_1"));
//    }
//
//    @Test
//    void testDeleteBookSuccess() throws Exception {
//        var bookId = "1";
//        doNothing().when(bookService).deleteById(bookId);
//
//        mockMvc.perform(delete("/books/{book_id}", bookId))
//                .andDo(print())
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    void testCreateBookSuccess() throws Exception {
//        BookCreateDto createDto = new BookCreateDto("Новая книга", 1L, 2L);
//        String json = new ObjectMapper().writeValueAsString(createDto);
//
//        mockMvc.perform(post("/books")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json))
//                .andDo(print())
//                .andExpect(status().isCreated());
//    }
//
//    @Test
//    void testUpdateBookSuccess() throws Exception {
//        BookUpdateDto updateDto = new BookUpdateDto(1L, "Обновленная книга", 2L, 3L);
//        String json = new ObjectMapper().writeValueAsString(updateDto);
//
//        mockMvc.perform(put("/books")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json))
//                .andDo(print())
//                .andExpect(status().isOk());
//    }
//
//    private List<GenreDto> getExpectedGenres() {
//        List<GenreDto> genres = new ArrayList<>();
//        genres.add(GenreDto.builder().id(1).name("Genre_1").build());
//        genres.add(GenreDto.builder().id(2).name("Genre_2").build());
//        genres.add(GenreDto.builder().id(3).name("Genre_3").build());
//        return genres;
//    }
//
//    private List<AuthorDto> getExpectedAuthors() {
//        List<AuthorDto> authors = new ArrayList<>();
//        authors.add(AuthorDto.builder().id(1).fullName("Author_1").build());
//        authors.add(AuthorDto.builder().id(2).fullName("Author_2").build());
//        authors.add(AuthorDto.builder().id(3).fullName("Author_3").build());
//        return authors;
//    }
//
//    private List<BookDto> getExpectedBooks() {
//        List<AuthorDto> authors = getExpectedAuthors();
//        List<GenreDto> genres = getExpectedGenres();
//        List<BookDto> books = new ArrayList<>();
//        books.add(BookDto.builder().id("1").title("BookTitle_1").author(authors.get(0)).genre(genres.get(0)).build());
//        books.add(BookDto.builder().id("2").title("BookTitle_2").author(authors.get(1)).genre(genres.get(1)).build());
//        books.add(BookDto.builder().id("3").title("BookTitle_3").author(authors.get(2)).genre(genres.get(2)).build());
//        return books;
//    }

}
