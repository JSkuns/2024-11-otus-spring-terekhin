package ru.otus.hw.controllers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import ru.otus.hw.dto.models.book.BookDto;
import ru.otus.hw.dto.models.comment.CommentCreateDto;
import ru.otus.hw.dto.models.comment.CommentDto;
import ru.otus.hw.dto.models.comment.CommentUpdateDto;
import ru.otus.hw.security.SecurityConfig;
import ru.otus.hw.services.CommentService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = {CommentsController.class, SecurityConfig.class})
public class CommentsControllerTest {

    @MockBean
    private CommentService commentService;

    @Autowired
    private MockMvc mockMvc;

    @WithMockUser
    @Test
    void shouldFindCommentsByBookId() throws Exception {
        long bookId = 1L;
        List<CommentDto> expectedComments = getExpectedComments();
        given(commentService.findAllCommentsByBookId(bookId)).willReturn(expectedComments);
        mockMvc.perform(get("/comments/find_by_book_id?book_id=" + bookId))
                .andExpect(status().isOk())
                .andExpect(model().attribute("comments", expectedComments));
    }

    @WithMockUser
    @Test
    void shouldInsertNewComment() throws Exception {
        CommentCreateDto commentCreateDto = new CommentCreateDto(1L, "test content");
        mockMvc.perform(post("/comments/create").with(csrf()).flashAttr("comment_create_obj", commentCreateDto))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/comments"));
        verify(commentService).create(commentCreateDto);
    }

    @WithMockUser
    @Test
    void shouldUpdateComment() throws Exception {
        CommentUpdateDto commentUpdateDto = new CommentUpdateDto(1L, "updated content");
        mockMvc.perform(post("/comments/update").with(csrf()).flashAttr("comment_update_obj", commentUpdateDto))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/comments"));
        verify(commentService).update(commentUpdateDto);
    }

    @WithMockUser
    @Test
    void shouldDeleteComment() throws Exception {
        mockMvc.perform(post("/comments/delete?comment_id=1").with(csrf()))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/comments"));
        verify(commentService).deleteById(1L);
    }

    @WithAnonymousUser
    @Test
    void shouldRedirectToLogin302() throws Exception {
        mockMvc.perform(post("/comments/delete?comment_id=1").with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
        verify(commentService, never()).deleteById(any(Long.class));
    }

    @WithMockUser
    @Test
    void shouldFindCommentById() throws Exception {
        long commentId = 1L;
        CommentDto expectedComment = getExpectedComments().get(0);
        given(commentService.findById(commentId)).willReturn(expectedComment);
        mockMvc.perform(get("/comments/find?comment_id=" + commentId))
                .andExpect(status().isOk())
                .andExpect(model().attribute("comments", List.of(expectedComment)));
    }

    @WithMockUser
    @Test
    void shouldThrownInternalServerError500() throws Exception {
        var result = mockMvc.perform(post("/commentsss/update?err").with(csrf()));
        result
                .andExpect(status().isOk())
                .andExpect(view().name("error"));
        Assertions.assertInstanceOf(NoResourceFoundException.class, result.andReturn().getResolvedException());
    }

    private List<CommentDto> getExpectedComments() {
        List<CommentDto> comments = new ArrayList<>();
        comments.add(CommentDto.builder().id(1L).text("Content_1").book(new BookDto()).build());
        comments.add(CommentDto.builder().id(2L).text("Content_2").book(new BookDto()).build());
        comments.add(CommentDto.builder().id(3L).text("Content_3").book(new BookDto()).build());
        return comments;
    }
}