package ru.otus.hw.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.hw.converters.CommentConverter;
import ru.otus.hw.services.CommentService;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@ShellComponent
public class CommentCommands {

    private final CommentService commentService;

    private final CommentConverter commentConverter;

    // (C)omment by (B)ook (ID) // Example: cbid 2
    @ShellMethod(value = "Find comment by book id", key = "cbid")
    public String findCommentByBookId(long bookId) {
        return commentService
                .findAllCommentsByBookId(bookId)
                .stream()
                .map(commentConverter::commentToString)
                .collect(Collectors.joining("," + System.lineSeparator()));
    }

    // (C)omment by comment (ID) // Example: cid 2
    @ShellMethod(value = "Find comment by id", key = "cid")
    public String findCommentById(long id) {
        return commentService
                .findById(id)
                .stream()
                .map(commentConverter::commentToString)
                .collect(Collectors.joining("," + System.lineSeparator()));
    }

    // (C)omment (DEL)ete // Example: cdel 4
    @ShellMethod(value = "Delete comment by id", key = "cdel")
    public void deleteBook(long id) {
        commentService.deleteById(id);
    }

    // (C)omment (INS)ert // Example: cins 2 www
    @ShellMethod(value = "Insert comment", key = "cins")
    public String insertComment(long bookId, String text) {
        var savedComment = commentService.insert(bookId, text);
        return commentConverter.commentToString(savedComment);
    }

    // (C)omment (UPD)ate // Example: cupd 2 2 qqq
    @ShellMethod(value = "Update book", key = "cupd")
    public String updateBook(long id, long bookId, String text) {
        var savedComment = commentService.update(id, bookId, text);
        return commentConverter.commentToString(savedComment);
    }

}
