package ru.otus.hw.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.dto.models.author.AuthorDto;
import ru.otus.hw.services.AuthorService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AuthorsController.class)
public class AuthorsControllerTest {

    @MockBean
    private AuthorService authorService;

    @Autowired
    private MockMvc mockMvc;

    @WithMockUser
    @Test
    void shouldReturnAllAuthors() throws Exception {
        List<AuthorDto> expectedAuthors = getExpectedAuthors();
        given(authorService.findAll()).willReturn(expectedAuthors);
        mockMvc.perform(get("/authors"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("authors", expectedAuthors));
    }

    @WithAnonymousUser
    @Test
    void shouldThrowUnauthorizedError401() throws Exception {
        mockMvc.perform(get("/authors"))
                .andExpect(status().isUnauthorized());
        verify(authorService, never()).findAll();
    }

    private List<AuthorDto> getExpectedAuthors() {
        List<AuthorDto> authors = new ArrayList<>();
        authors.add(AuthorDto.builder().id(1).fullName("Author_1").build());
        authors.add(AuthorDto.builder().id(2).fullName("Author_2").build());
        authors.add(AuthorDto.builder().id(3).fullName("Author_3").build());
        return authors;
    }

}