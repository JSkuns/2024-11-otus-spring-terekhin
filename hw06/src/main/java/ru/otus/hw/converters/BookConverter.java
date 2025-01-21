package ru.otus.hw.converters;

import lombok.RequiredArgsConstructor;
import org.hibernate.LazyInitializationException;
import org.springframework.stereotype.Component;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;

@RequiredArgsConstructor
@Component
public class BookConverter {

    private final AuthorConverter authorConverter;

    private final GenreConverter genreConverter;

    /**
     * Для вывода в консоль информации о книге.
     * Если связь с FK -> Lazy, то будет LazyInitializationException, и соответственно на вывод доступно только ID
     */
    public String bookToString(Book book) {
        String title;
        Author author;
        Genre genre;
        try {
            title = book.getTitle();
            author = book.getAuthor();
            genre = book.getGenre();
        } catch (LazyInitializationException ex) {
            return "Id: %d".formatted(book.getId());
        }
        return "Id: %d, title: %s, author: {%s}, genres: [%s]".formatted(
                book.getId(),
                title,
                authorConverter.authorToString(author),
                genreConverter.genreToString(genre));
    }

}
