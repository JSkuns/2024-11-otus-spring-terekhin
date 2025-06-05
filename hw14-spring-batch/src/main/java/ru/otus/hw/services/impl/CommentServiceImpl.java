package ru.otus.hw.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.h2.Comment;
import ru.otus.hw.repositories.h2.BookRepository;
import ru.otus.hw.repositories.h2.CommentRepository;
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
    public List<Comment> findAllCommentsByBookId(long id) {
        return commentRepository.findByBookId(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Comment> findById(long id) {
        return commentRepository.findById(id);
    }

    @Override
    @Transactional
    public Comment insert(long bookId, String text) {
        var book = bookRepository.findById(bookId)
                .orElseThrow(() -> {
                    var errMsg = "Book with id %d not found".formatted(bookId);
                    log.error(errMsg);
                    return new EntityNotFoundException(errMsg);
                });
        return commentRepository.save(new Comment(0, text, book));
    }

    @Override
    @Transactional
    public Comment update(long id, String text) {
        var comment = findById(id)
                .orElseThrow(() -> {
                    var errMsg = "Comment with id %d not found".formatted(id);
                    log.error(errMsg);
                    return new EntityNotFoundException(errMsg);
                });
        comment.setText(text);
        var savedComment = commentRepository.save(comment);
        commentRepository.flush();
        log.info("The comment with id %d has been changed".formatted(id));
        return savedComment;
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        commentRepository.deleteById(id);
        log.info("Comment with id %d was deleted".formatted(id));
    }

}