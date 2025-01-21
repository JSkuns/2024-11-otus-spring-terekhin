package ru.otus.hw.services;

import ru.otus.hw.models.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentService {

    List<Comment> findAllCommentsByBookId(long id);

    Optional<Comment> findById(long id);

    Comment insert(long bookId, String text);

    Comment update(long commentId, long bookId, String text);

    void deleteById(long id);

}
