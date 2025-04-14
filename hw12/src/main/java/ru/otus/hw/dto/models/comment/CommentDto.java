package ru.otus.hw.dto.models.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.otus.hw.dto.models.book.BookDto;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class CommentDto {

    private long id;

    private BookDto book;

    private String text;

}
