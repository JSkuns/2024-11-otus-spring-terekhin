package ru.otus.hw.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.hw.converters.CommentConverter;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.services.CommentService;

import java.util.stream.Collectors;

@SuppressWarnings({"SpellCheckingInspection", "unused"})
@RequiredArgsConstructor
@ShellComponent
public class CommentCommands {

    private final CommentService commentService;

    private final CommentConverter commentConverter;

    /**
     * (C)omment by (B)ook (ID)
     * Example: cbid 2
     */
    @ShellMethod(value = "Find comments by book id", key = "cbid")
    public String findAllCommentsByBookId(long bookId) {
        return commentService
                .findAllCommentsByBookId(bookId)
                .stream()
                .map(commentConverter::commentToString)
                .collect(Collectors.joining("," + System.lineSeparator()));
    }

    /**
     * (C)omment by comment (ID)
     * Example: cid 2
     */
    @ShellMethod(value = "Find comment by id", key = "cid")
    public String findCommentById(long id) {
        return commentService
                .findById(id)
                .map(commentConverter::commentToString)
                .orElseThrow(() -> new EntityNotFoundException("Comment with id %d not found".formatted(id)));
    }

    /**
     * (C)omment (DEL)ete
     * Example: cdel 4
     */
    @ShellMethod(value = "Delete comment by id", key = "cdel")
    public void deleteBook(long id) {
        commentService.deleteById(id);
        System.out.printf("Comment with id %d was deleted\n", id);
    }

    /**
     * (C)omment (INS)ert
     * Example 1: cins 2 www
     * Example 2: cins 2 "www trtrrtrtr"
     */
    @ShellMethod(value = "Insert comment", key = "cins")
    public String insertComment(long bookId, String text) {
        var savedComment = commentService.insert(bookId, text);
        return commentConverter.commentToString(savedComment);
    }

    /**
     * (C)omment (UPD)ate
     * Example 1: cupd 2 qqq
     * Example 2: cupd 1 "qqq www eee rrr ttt yyy"
     */
    @ShellMethod(value = "Update comment", key = "cupd")
    public String updateComment(long id, String text) {
        var savedComment = commentService.update(id, text);
        return commentConverter.commentToString(savedComment);
    }

}
