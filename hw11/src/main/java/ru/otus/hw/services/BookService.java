package ru.otus.hw.services;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw.dto.models.book.BookCreateDto;
import ru.otus.hw.dto.models.book.BookDto;
import ru.otus.hw.dto.models.book.BookUpdateDto;

public interface BookService {

    Mono<BookDto> findById(String id);

    Flux<BookDto> findAll();

    Mono<BookDto> create(BookCreateDto dto);

    Mono<BookDto> update(BookUpdateDto dto);

    Mono<Void> deleteById(String id);

}
