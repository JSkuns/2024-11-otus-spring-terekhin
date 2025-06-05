package ru.otus.hw.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.dto.models.genre.GenreDto;
import ru.otus.hw.services.GenreService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = GenresController.class)
public class GenresControllerTest {

    @MockBean
    private GenreService genreService;

    @Autowired
    private MockMvc mockMvc;

    @WithMockUser
    @Test
    void shouldReturnAllGenres() throws Exception {
        List<GenreDto> expectedGenres = getExpectedGenres();
        given(genreService.findAll()).willReturn(expectedGenres);
        mockMvc.perform(get("/genres"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("genres", expectedGenres));
    }

    @WithAnonymousUser
    @Test
    void shouldThrowUnauthorizedError401() throws Exception {
        mockMvc.perform(get("/genres"))
                .andExpect(status().isUnauthorized());
        verify(genreService, never()).findAll();
    }

    private List<GenreDto> getExpectedGenres() {
        List<GenreDto> genres = new ArrayList<>();
        genres.add(GenreDto.builder().id(1).name("Genre_1").build());
        genres.add(GenreDto.builder().id(2).name("Genre_2").build());
        genres.add(GenreDto.builder().id(3).name("Genre_3").build());
        return genres;
    }

}