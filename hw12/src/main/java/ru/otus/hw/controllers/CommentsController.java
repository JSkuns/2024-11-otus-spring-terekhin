package ru.otus.hw.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.hw.dto.models.comment.CommentCreateDto;
import ru.otus.hw.dto.models.comment.CommentDto;
import ru.otus.hw.dto.models.comment.CommentUpdateDto;
import ru.otus.hw.services.CommentService;

import java.util.List;

/**
 * <a href="http://localhost:8080/comments">...</a>
 */
@Controller
@RequiredArgsConstructor
public class CommentsController {

    private final CommentService commentService;

    @GetMapping(path = "/comments")
    public String index(Model model) {
        addObjectsOnView(model);
        return "comments";
    }

    @GetMapping(path = "/comments/find_by_book_id")
    public String findCommentsByBookId(@RequestParam(value = "book_id") String bookId, Model model) {
        var comments = commentService.findAllCommentsByBookId(Long.parseLong(bookId));
        model.addAttribute("comments", comments);
        addObjectsOnView(model);
        return "comments";
    }

    @PostMapping(path = "/comments/delete")
    public String deleteComment(@RequestParam(value = "comment_id") String id) {
        commentService.deleteById(Long.parseLong(id));
        return "redirect:/comments";
    }

    @PostMapping(path = "/comments/create")
    public String createComment(@Valid @ModelAttribute(value = "comment_create_obj") CommentCreateDto commentCreateDto) {
        commentService.create(commentCreateDto);
        return "redirect:/comments";
    }

    @PostMapping(path = "/comments/update")
    public String updateComment(@Valid @ModelAttribute(value = "comment_update_obj") CommentUpdateDto commentUpdateDto) {
        commentService.update(commentUpdateDto);
        return "redirect:/comments";
    }

    @GetMapping(path = "/comments/find")
    public String findCommentById(@RequestParam(value = "comment_id") String id, Model model) {
        var comment = commentService.findById(Long.parseLong(id));
        List<CommentDto> comments = List.of(comment);
        model.addAttribute("comments", comments);
        addObjectsOnView(model);
        return "comments";
    }

    private void addObjectsOnView(Model model) {
        model.addAttribute("comment_create_obj", new CommentCreateDto());
        model.addAttribute("comment_update_obj", new CommentUpdateDto());
    }

}
