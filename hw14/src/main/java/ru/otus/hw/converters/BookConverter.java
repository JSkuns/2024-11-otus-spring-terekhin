package ru.otus.hw.converters;

import lombok.RequiredArgsConstructor;
import org.hibernate.LazyInitializationException;
import org.springframework.stereotype.Component;
import ru.otus.hw.models.h2.Book;

@RequiredArgsConstructor
@Component
public class BookConverter {

    private final AuthorConverter authorConverter;

    private final GenreConverter genreConverter;

    public String bookToString(Book book) {
        try {
            return "Id: %d, title: %s, author: {%s}, genres: [%s]"
                    .formatted(
                            book.getId(),
                            book.getTitle(),
                            authorConverter.authorToString(book.getAuthor()),
                            genreConverter.genreToString(book.getGenre())
                    );
        } catch (LazyInitializationException ex) {
            return "Id: %d".formatted(book.getId());
        }
    }

}
