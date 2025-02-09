package ru.otus.hw.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Comment;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.CommentRepository;
import ru.otus.hw.services.CommentService;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final BookRepository bookRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Comment> findAllCommentsByBookId(String id) {
        return commentRepository.findByBookId(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Comment> findById(String id) {
        return commentRepository.findById(id);
    }

    @Override
    @Transactional
    public Comment insert(String bookId, String text) {
        var book = bookRepository.findById(bookId)
                .orElseThrow(() -> {
                    var errMsg = "Book with id %s not found".formatted(bookId);
                    log.error(errMsg);
                    return new EntityNotFoundException(errMsg);
                });
        return commentRepository.save(new Comment(null, text, book));
    }

    @Override
    @Transactional
    public Comment update(String id, String text) {
        var comment = findById(id)
                .orElseThrow(() -> {
                    var errMsg = "Comment with id %s not found".formatted(id);
                    log.error(errMsg);
                    return new EntityNotFoundException(errMsg);
                });
        comment.setText(text);
        var savedComment = commentRepository.save(comment);
        log.info("The comment with id %s has been changed".formatted(id));
        return savedComment;
    }

    @Override
    @Transactional
    public void deleteById(String id) {
        commentRepository.deleteById(id);
        log.info("Comment with id %s was deleted".formatted(id));
    }

}