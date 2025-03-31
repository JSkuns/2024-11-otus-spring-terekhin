package ru.otus.hw.dto.models.book;

import lombok.*;
import ru.otus.hw.dto.models.author.AuthorDto;
import ru.otus.hw.dto.models.genre.GenreDto;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {

    private String id;

    private String title;

    private AuthorDto author;

    private GenreDto genre;

}
