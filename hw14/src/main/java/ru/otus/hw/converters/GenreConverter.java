package ru.otus.hw.converters;

import org.hibernate.LazyInitializationException;
import org.springframework.stereotype.Component;
import ru.otus.hw.models.h2.Genre;

@Component
public class GenreConverter {

    public String genreToString(Genre genre) {
        try {
            return "Id: %d, Name: %s"
                    .formatted(
                            genre.getId(),
                            genre.getName()
                    );
        } catch (LazyInitializationException ex) {
            return "Id: %d".formatted(genre.getId());
        }
    }

}
