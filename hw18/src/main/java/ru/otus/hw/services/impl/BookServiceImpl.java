package ru.otus.hw.services.impl;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.dto.mappers.impl.BookDtoMapper;
import ru.otus.hw.dto.models.author.AuthorDto;
import ru.otus.hw.dto.models.book.BookCreateDto;
import ru.otus.hw.dto.models.book.BookDto;
import ru.otus.hw.dto.models.book.BookUpdateDto;
import ru.otus.hw.dto.models.genre.GenreDto;
import ru.otus.hw.exceptions.NotFoundException;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.AuthorRepository;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.GenreRepository;
import ru.otus.hw.services.BookService;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    private final BookRepository bookRepository;

    private final BookDtoMapper bookDtoMapper;

    @Override
    @Transactional(readOnly = true)
    @CircuitBreaker(name = "bookCB", fallbackMethod = "fallbackFindById")
    @Retry(name = "bookR")
    @Bulkhead(name = "bookBH", type = Bulkhead.Type.THREADPOOL)
    public BookDto findById(long id) {
        var book = bookRepository.findById(id).orElse(null);
        return bookDtoMapper.toDto(Objects.requireNonNull(book));
    }

    public BookDto fallbackFindById(long id, Throwable t) {
        log.warn("Error finding book by ID {}: {}", id, t.getMessage());
        return new BookDto(
                -1L,
                "No title",
                new AuthorDto(-1L, "Nobody"),
                new GenreDto(-1L, "No genre")
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDto> findAll() {
        List<Book> books = bookRepository.findAll();
        return bookDtoMapper.toDto(books);
    }

    @Override
    @Transactional
    public BookDto create(BookCreateDto createDto) {
        var author = findAuthorById(createDto.getAuthorId());
        var genre = findGenreById(createDto.getGenreId());
        var book = new Book(0, createDto.getTitle(), author, genre);
        var retVal = bookRepository.save(book);
        return bookDtoMapper.toDto(retVal);
    }

    @Override
    @Transactional
    public BookDto update(BookUpdateDto updateDto) {
        var book = bookRepository.findById(updateDto.getId())
                .orElseThrow(() -> {
                    var errMsg = "Book with id %s not found".formatted(updateDto.getId());
                    log.error(errMsg);
                    return new NotFoundException(errMsg);
                });
        var author = findAuthorById(updateDto.getAuthorId());
        var genre = findGenreById(updateDto.getGenreId());

        book.setTitle(updateDto.getTitle());
        book.setAuthor(author);
        book.setGenre(genre);

        var retVal = bookRepository.save(book);
        return bookDtoMapper.toDto(retVal);
    }

    @Override
    @Transactional
    @Secured("ROLE_ADMIN")
    public void deleteById(long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication == null ? "Unknown" : authentication.getName();
        bookRepository.deleteById(id);
        log.info("Book with id %d was deleted by user - %s".formatted(id, username));
    }

    private Genre findGenreById(long genreId) {
        return genreRepository.findById(genreId)
                .orElseThrow(() -> {
                    var errMsg = "Genre with id %s not found".formatted(genreId);
                    log.error(errMsg);
                    return new NotFoundException(errMsg);
                });
    }

    private Author findAuthorById(long authorId) {
        return authorRepository.findById(authorId)
                .orElseThrow(() -> {
                    var errMsg = "Author with id %s not found".formatted(authorId);
                    log.error(errMsg);
                    return new NotFoundException(errMsg);
                });
    }

}
