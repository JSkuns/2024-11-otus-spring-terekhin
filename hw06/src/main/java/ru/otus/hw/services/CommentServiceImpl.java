package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Comment;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.CommentRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final BookRepository bookRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Comment> findAllCommentsByBookId(long id) {
        return commentRepository.findAllCommentsByBookId(id);
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
                .orElseThrow(() -> new EntityNotFoundException("Book with id %d not found".formatted(bookId)));
        return commentRepository.save(new Comment(0, text, book));
    }

    @Override
    @Transactional
    public Comment update(long id, String text) {
        var existedComment = findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Comment with id %d not found".formatted(id)));
        return commentRepository.save(new Comment(existedComment.getId(), text, existedComment.getBook()));
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        var comment = findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Comment with id %d not found".formatted(id)));
        commentRepository.delete(comment);
        System.out.printf("Comment with id %d was deleted\n", id);
    }

}
