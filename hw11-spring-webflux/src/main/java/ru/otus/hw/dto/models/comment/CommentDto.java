package ru.otus.hw.dto.models.comment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.otus.hw.dto.models.book.BookDto;

@Getter
@Setter
@AllArgsConstructor
public class CommentDto {

    private String id;

    private BookDto book;

    private String text;

}
