package ru.otus.hw.controllers.rest;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.hw.dto.models.book.BookDto;
import ru.otus.hw.services.BookService;

@WebMvcTest(BooksRestController.class)
public class BooksRestControllerTest {

    @MockBean
    private BookService bookService;

    @Test
    void shouldReturnCorrectBooksList() throws Exception {
//        List<Person> persons = List.of(new Person(1, "Person1"), new Person(2, "Person2"));
//        given(bookService.findAll()).willReturn(persons);
//
//        List<BookDto> expectedResult = persons.stream()
//                .map(BookDto::toDto).collect(Collectors.toList());
//
//        mvc.perform(get("/api/persons"))
//                .andExpect(status().isOk())
//                .andExpect(content().json(mapper.writeValueAsString(expectedResult)));
    }

}
