package ru.otus.hw.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.hw.exceptions.NotFoundException;
import ru.otus.hw.models.Comment;
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
    public String index() {
        return "comments";
    }

    @GetMapping(path = "/comments/find_by_book_id")
    public String findCommentsByBookId(
            @RequestParam(value = "book_id") String bookId,
            Model model
    ) {
        var idLong = parseIdFromStringToLong(bookId);
        if (idLong == null) {
            return "redirect:/error";
        }
        var comments = commentService.findAllCommentsByBookId(idLong);
        model.addAttribute("comments", comments);
        return "comments";
    }

    @PostMapping(path = "/comments/delete")
    public String deleteComment(@RequestParam(value = "comment_id") String id) {
        var idLong = parseIdFromStringToLong(id);
        if (idLong == null) {
            return "redirect:/error";
        }
        commentService.deleteById(Long.parseLong(id));
        return "redirect:/comments";
    }

    @PostMapping(path = "/comments/insert")
    public String insertBook(
            @RequestParam(value = "book_id") String bookId,
            @RequestParam(value = "text") String text
    ) {
        var bookIdLong = parseIdFromStringToLong(bookId);
        if (bookIdLong == null) {
            return "redirect:/error";
        }
        try {
            commentService.insert(bookIdLong, text);
        } catch (NotFoundException e) {
            return "redirect:/comments";
        }
        return "redirect:/comments";
    }

    @PostMapping(path = "/comments/update")
    public String updateBook(
            @RequestParam(value = "id") String id,
            @RequestParam(value = "text") String text
    ) {
        var idLong = parseIdFromStringToLong(id);
        if (idLong == null) {
            return "redirect:/error";
        }
        try {
            commentService.update(idLong, text);
        } catch (NotFoundException e) {
            return "redirect:/comments";
        }
        return "redirect:/comments";
    }

    @GetMapping(path = "/comments/find")
    public String findCommentById(
            @RequestParam(value = "comment_id") String id,
            Model model
    ) {
        var idLong = parseIdFromStringToLong(id);
        if (idLong == null) {
            return "redirect:/error";
        }
        var comment = commentService.findById(idLong).orElse(null);
        if (comment == null) {
            return "redirect:/comments";
        }
        List<Comment> comments = List.of(comment);
        model.addAttribute("comments", comments);
        return "comments";
    }

    private Long parseIdFromStringToLong(String val) {
        try {
            return Long.parseLong(val);
        } catch (NumberFormatException ex) {
            return null;
        }
    }

}
