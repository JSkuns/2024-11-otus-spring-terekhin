package ru.otus.hw.dto.models.book;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;
import ru.otus.hw.dto.models.author.AuthorDto;
import ru.otus.hw.dto.models.genre.GenreDto;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {

    private long id;

    private String title;

    private AuthorDto author;

    private GenreDto genre;

}
