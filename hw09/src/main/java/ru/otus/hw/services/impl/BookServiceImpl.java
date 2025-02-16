package ru.otus.hw.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.AuthorRepository;
import ru.otus.hw.repositories.BookRepository;
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
        var author = findAuthorById(authorId);
        var genre = findGenreById(genreId);
        var book = new Book(0, title, author, genre);
        return bookRepository.save(book);
    }

    @Override
    @Transactional
    public Book update(long id, String title, long authorId, long genreId) {
        var book = bookRepository.findById(id)
                .orElseThrow(() -> {
                    var errMsg = "Book with id %s not found".formatted(id);
                    log.error(errMsg);
                    return new EntityNotFoundException(errMsg);
                });
        var author = findAuthorById(authorId);
        var genre = findGenreById(genreId);

        book.setTitle(title);
        book.setAuthor(author);
        book.setGenre(genre);

        return bookRepository.save(book);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        bookRepository.deleteById(id);
        log.info("Book with id %d was deleted".formatted(id));
    }

    private Genre findGenreById(long genreId) {
        return genreRepository.findById(genreId)
                .orElseThrow(() -> {
                    var errMsg = "Genre with id %s not found".formatted(genreId);
                    log.error(errMsg);
                    return new EntityNotFoundException(errMsg);
                });
    }

    private Author findAuthorById(long authorId) {
        return authorRepository.findById(authorId)
                .orElseThrow(() -> {
                    var errMsg = "Author with id %s not found".formatted(authorId);
                    log.error(errMsg);
                    return new EntityNotFoundException(errMsg);
                });
    }

}
