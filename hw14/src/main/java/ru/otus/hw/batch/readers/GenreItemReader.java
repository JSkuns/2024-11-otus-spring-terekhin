package ru.otus.hw.batch.readers;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;
import ru.otus.hw.models.h2.Genre;
import ru.otus.hw.repositories.h2.GenreRepository;

import java.util.Iterator;
import java.util.List;

@Component
@AllArgsConstructor
public class GenreItemReader implements ItemReader<Genre> {

    private static Iterator<Genre> iterator;

    private GenreRepository genreRepository;

    @PostConstruct
    public void initialize() {
        List<Genre> genres = genreRepository.findAll();
        iterator = genres.iterator();
    }

    @Override
    public Genre read() {
        if (!iterator.hasNext()) {
            return null;
        }
        return iterator.next();
    }

}
