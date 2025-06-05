package ru.otus.hw.batch.readers;

import jakarta.annotation.PostConstruct;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;
import ru.otus.hw.models.h2.Book;
import ru.otus.hw.repositories.h2.BookRepository;

import java.util.Iterator;
import java.util.List;

@Component
public class BookItemReader implements ItemReader<Book> {

    private final BookRepository bookRepository;

    private Iterator<Book> iterator;

    public BookItemReader(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @PostConstruct
    public void initialize() {
        List<Book> books = bookRepository.findAll();
        iterator = books.iterator();
    }

    @Override
    public Book read() {
        if (!iterator.hasNext()) {
            return null;
        }
        return iterator.next();
    }

}
