package ru.otus.hw.services;

import ru.otus.hw.models.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentService {

    List<Comment> findAllCommentsByBookId(String id);

    Optional<Comment> findById(String id);

    Comment insert(String bookId, String text);

    Comment update(String commentId, String text);

    void deleteById(String id);

}
