package ru.otus.hw.batch.processors;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import ru.otus.hw.models.h2.Author;
import ru.otus.hw.models.h2.Book;
import ru.otus.hw.models.h2.Genre;
import ru.otus.hw.models.mongo.MongoAuthor;
import ru.otus.hw.models.mongo.MongoBook;
import ru.otus.hw.models.mongo.MongoGenre;

@Component
public class BookItemProcessor implements ItemProcessor<Book, MongoBook> {

    @Override
    public MongoBook process(Book book) {
        MongoBook mongoBook = new MongoBook();
        mongoBook.setId(String.valueOf(book.getId()));
        mongoBook.setMongoAuthor(mapAuthor(book.getAuthor()));
        mongoBook.setMongoGenre(mapGenre(book.getGenre()));
        mongoBook.setTitle(book.getTitle());
        return mongoBook;
    }

    private MongoAuthor mapAuthor(Author author) {
        return new AuthorItemProcessor().process(author);
    }

    private MongoGenre mapGenre(Genre genre) {
        return new GenreItemProcessor().process(genre);
    }

}
