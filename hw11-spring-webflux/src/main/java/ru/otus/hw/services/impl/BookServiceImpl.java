package ru.otus.hw.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuples;
import ru.otus.hw.dto.mappers.impl.BookDtoMapper;
import ru.otus.hw.dto.models.book.BookCreateDto;
import ru.otus.hw.dto.models.book.BookDto;
import ru.otus.hw.dto.models.book.BookUpdateDto;
import ru.otus.hw.exceptions.NotFoundException;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.AuthorRepository;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.GenreRepository;
import ru.otus.hw.services.BookService;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    private final BookRepository bookRepository;

    private final BookDtoMapper bookDtoMapper;

    @Override
    public Mono<BookDto> findById(String id) {
        return bookRepository.findById(id).map(bookDtoMapper::toDto);
    }

    @Override
    public Flux<BookDto> findAll() {
        return bookRepository.findAll().map(bookDtoMapper::toDto);
    }

    @Override
    public Mono<BookDto> create(BookCreateDto createDto) {
        return Mono.zip(
                        findAuthorById(createDto.getAuthorId()),
                        findGenreById(createDto.getGenreId()))
                .map(tuple -> {
                    var author = tuple.getT1();
                    var genre = tuple.getT2();
                    return new Book(null, createDto.getTitle(), author, genre);
                })
                .flatMap(bookRepository::save) // Сохранение книги
                .map(bookDtoMapper::toDto); // Преобразование в DTO
    }

    @Override
    public Mono<BookDto> update(BookUpdateDto updateDto) {
        return bookRepository.findById(updateDto.getId())
                .switchIfEmpty(Mono.error(
                        new NotFoundException("Book with id %s not found".formatted(updateDto.getId()))))
                .flatMap(book -> authorRepository.findById(updateDto.getAuthorId())
                            .switchIfEmpty(Mono.error(new NotFoundException("Author with id %s not found"
                                    .formatted(updateDto.getAuthorId()))))
                            .zipWith(genreRepository.findById(updateDto.getGenreId())
                                            .switchIfEmpty(Mono.error(new NotFoundException("Genre with id %s not found"
                                                    .formatted(updateDto.getGenreId())))),
                                    (author, genre) -> Tuples.of(book, author, genre)
                            ))
                .map(tuple -> {
                    Book book = tuple.getT1();
                    Author author = tuple.getT2();
                    Genre genre = tuple.getT3();
                    book.setTitle(updateDto.getTitle());
                    book.setAuthor(author);
                    book.setGenre(genre);
                    return book;
                })
                .flatMap(bookRepository::save) // Сохраняем книгу реактивно
                .map(bookDtoMapper::toDto); // Преобразуем результат в DTO
    }

    @Override
    public Mono<Void> deleteById(String id) {
        var retVal = bookRepository.deleteById(id);
        log.info("Book with id %s was deleted".formatted(id));
        return retVal;
    }

    private Mono<Genre> findGenreById(String genreId) {
        return genreRepository.findById(genreId)
                .switchIfEmpty(Mono.error(new NotFoundException("Genre with id %s not found".formatted(genreId))));
    }

    private Mono<Author> findAuthorById(String authorId) {
        return authorRepository.findById(authorId)
                .switchIfEmpty(Mono.error(new NotFoundException("Author with id %s not found".formatted(authorId))));
    }

}