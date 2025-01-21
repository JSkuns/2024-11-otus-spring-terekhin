package ru.otus.hw.converters;

import org.hibernate.LazyInitializationException;
import org.springframework.stereotype.Component;
import ru.otus.hw.models.Author;

@Component
public class AuthorConverter {

    public String authorToString(Author author) {
        String fullName;
        try {
            fullName = author.getFullName();
        } catch (LazyInitializationException ex) {
            return "Id: %d".formatted(author.getId());
        }
        return "Id: %d, FullName: %s".formatted(author.getId(), fullName);
    }

}
