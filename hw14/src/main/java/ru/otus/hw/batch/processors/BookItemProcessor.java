package ru.otus.hw.batch.processors;

import lombok.AllArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import ru.otus.hw.models.h2.Book;
import ru.otus.hw.models.mongo.MongoBook;
import ru.otus.hw.repositories.mongo.MongoAuthorRepository;
import ru.otus.hw.repositories.mongo.MongoGenreRepository;

@AllArgsConstructor
@Component
public class BookItemProcessor implements ItemProcessor<Book, MongoBook> {

    private final MongoAuthorRepository mongoAuthorRepository;

    private final MongoGenreRepository mongoGenreRepository;

    @Override
    public MongoBook process(Book book) {
        MongoBook mongoBook = new MongoBook();
        mongoBook.setId(String.valueOf(book.getId()));
        var mongoAuthor = mongoAuthorRepository
                .findById(String.valueOf(book.getAuthor().getId()))
                .orElse(null);
        mongoBook.setMongoAuthor(mongoAuthor);
        var mongoGenre = mongoGenreRepository
                .findById(String.valueOf(book.getGenre().getId()))
                .orElse(null);
        mongoBook.setMongoGenre(mongoGenre);
        mongoBook.setTitle(book.getTitle());
        return mongoBook;
    }

}
