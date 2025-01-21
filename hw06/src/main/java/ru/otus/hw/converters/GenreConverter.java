package ru.otus.hw.converters;

import org.hibernate.LazyInitializationException;
import org.springframework.stereotype.Component;
import ru.otus.hw.models.Genre;

@Component
public class GenreConverter {

    public String genreToString(Genre genre) {
        String name;
        try {
            name = genre.getName();
        } catch (LazyInitializationException ex) {
            return "Id: %d".formatted(genre.getId());
        }
        return "Id: %d, Name: %s".formatted(genre.getId(), name);
    }

}
