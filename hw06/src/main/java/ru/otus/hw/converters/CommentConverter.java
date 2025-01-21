package ru.otus.hw.converters;

import lombok.RequiredArgsConstructor;
import org.hibernate.LazyInitializationException;
import org.springframework.stereotype.Component;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;

@RequiredArgsConstructor
@Component
public class CommentConverter {

    private final BookConverter bookConverter;

    public String commentToString(Comment comment) {
        String text;
        Book book;
        try {
            text = comment.getText();
            book = comment.getBook();
        } catch (LazyInitializationException ex) {
            return "Id: %d".formatted(comment.getId());
        }
        return "Id: %d, Text: %s, Book: %s".formatted(
                comment.getId(),
                text,
                bookConverter.bookToString(book));
    }

}