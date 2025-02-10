package ru.otus.hw.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Book;
import ru.otus.hw.repositories.AuthorRepository;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.CommentRepository;
import ru.otus.hw.repositories.GenreRepository;
import ru.otus.hw.services.BookService;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    private final BookRepository bookRepository;

    private final CommentRepository commentRepository;

    @Override
    public Optional<Book> findById(String id) {
        return bookRepository.findById(id);
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book insert(String title, String authorId, String genreId) {
        return save(null, title, authorId, genreId);
    }

    @Override
    public Book update(String id, String title, String authorId, String genreId) {
        bookRepository.findById(id)
                .orElseThrow(() -> {
                    var errMsg = "Book with id %s not found".formatted(id);
                    log.error(errMsg);
                    return new EntityNotFoundException(errMsg);
                });
        return save(id, title, authorId, genreId);
    }

    @Override
    public void deleteById(String id) {
        deleteAllCommentsToTheBook(id);
        bookRepository.deleteById(id);
        log.info("Book with id %s was deleted".formatted(id));
    }

    /**
     * Удалим все комментарии, которые относятся к книге
     */
    private void deleteAllCommentsToTheBook(String id) {
        commentRepository.deleteByBookId(id);
        log.info("Comments on the book with id %s have been deleted".formatted(id));
    }

    private Book save(String id, String title, String authorId, String genreId) {
        var author = authorRepository.findById(authorId)
                .orElseThrow(() -> {
                    var errMsg = "Author with id %s not found".formatted(authorId);
                    log.error(errMsg);
                    return new EntityNotFoundException(errMsg);
                });
        var genre = genreRepository.findById(genreId)
                .orElseThrow(() -> {
                    var errMsg = "Genre with id %s not found".formatted(genreId);
                    log.error(errMsg);
                    return new EntityNotFoundException(errMsg);
                });
        var book = new Book(id, title, author, genre);
        return bookRepository.save(book);
    }

}
