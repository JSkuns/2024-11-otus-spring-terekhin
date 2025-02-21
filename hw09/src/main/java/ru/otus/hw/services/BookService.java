package ru.otus.hw.services;

import ru.otus.hw.dto.models.book.BookCreateDto;
import ru.otus.hw.dto.models.book.BookDto;
import ru.otus.hw.dto.models.book.BookUpdateDto;

import java.util.List;

public interface BookService {

    BookDto findById(long id);

    List<BookDto> findAll();

    BookDto create(BookCreateDto dto);

    BookDto update(BookUpdateDto dto);

    void deleteById(long id);

}
