package ru.otus.hw.batch.readers;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;
import ru.otus.hw.models.h2.Author;
import ru.otus.hw.repositories.h2.AuthorRepository;

import java.util.Iterator;
import java.util.List;

@Component
@AllArgsConstructor
public class AuthorItemReader implements ItemReader<Author> {

    private static Iterator<Author> iterator;

    private final AuthorRepository authorRepository;

    @PostConstruct
    public void initialize() {
        List<Author> authors = authorRepository.findAll();
        iterator = authors.iterator();
    }

    @Override
    public Author read() {
        if (!iterator.hasNext()) {
            return null;
        }
        return iterator.next();
    }

}
