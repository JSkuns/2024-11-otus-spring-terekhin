package ru.otus.hw.converters;

import org.hibernate.LazyInitializationException;
import org.springframework.stereotype.Component;
import ru.otus.hw.models.h2.Author;

@Component
public class AuthorConverter {

    public String authorToString(Author author) {
        try {
            return "Id: %d, FullName: %s"
                    .formatted(
                            author.getId(),
                            author.getFullName()
                    );
        } catch (LazyInitializationException ex) {
            return "Id: %d".formatted(author.getId());
        }
    }

}
