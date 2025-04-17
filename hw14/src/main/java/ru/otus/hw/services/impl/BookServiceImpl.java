package ru.otus.hw.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.h2.Book;
import ru.otus.hw.repositories.h2.AuthorRepository;
import ru.otus.hw.repositories.h2.BookRepository;
import ru.otus.hw.repositories.h2.GenreRepository;
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

    @Override
    @Transactional(readOnly = true)
    public Optional<Book> findById(long id) {
        return bookRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    @Transactional
    public Book insert(String title, long authorId, long genreId) {
        return save(0, title, authorId, genreId);
    }

    @Override
    @Transactional
    public Book update(long id, String title, long authorId, long genreId) {
        bookRepository.findById(id)
                .orElseThrow(() -> {
                    var errMsg = "Book with id %d not found".formatted(id);
                    log.error(errMsg);
                    return new EntityNotFoundException(errMsg);
                });
        return save(id, title, authorId, genreId);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        bookRepository.deleteById(id);
        log.info("Book with id %d was deleted".formatted(id));
    }

    @Transactional
    private Book save(long id, String title, long authorId, long genreId) {
        var author = authorRepository.findById(authorId)
                .orElseThrow(() -> {
                    var errMsg = "Author with id %d not found".formatted(authorId);
                    log.error(errMsg);
                    return new EntityNotFoundException(errMsg);
                });
        var genre = genreRepository.findById(genreId)
                .orElseThrow(() -> {
                    var errMsg = "Genre with id %d not found".formatted(genreId);
                    log.error(errMsg);
                    return new EntityNotFoundException(errMsg);
                });
        var book = new Book(id, title, author, genre);
        return bookRepository.save(book);
    }

}
