package ru.otus.hw.dto.mappers.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.dto.mappers.DtoMapper;
import ru.otus.hw.dto.models.book.BookDto;
import ru.otus.hw.models.Book;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class BookDtoMapper implements DtoMapper<BookDto, Book> {

    private final AuthorDtoMapper authorDtoMapper;

    private final GenreDtoMapper genreDtoMapper;

    @Override
    public BookDto toDto(Book source) {
        BookDto dto = new BookDto();
        dto.setId(source.getId());
        dto.setTitle(source.getTitle());
        dto.setAuthor(authorDtoMapper.toDto(source.getAuthor()));
        dto.setGenre(genreDtoMapper.toDto(source.getGenre()));
        return dto;
    }

    public List<BookDto> toDto(List<Book> bookList) {
        return bookList
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Book toModel(BookDto source) {
        return null;
    }

}
