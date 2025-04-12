package ru.otus.hw.batch.processors;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import ru.otus.hw.models.h2.Author;
import ru.otus.hw.models.mongo.MongoAuthor;

@Component
public class AuthorItemProcessor implements ItemProcessor<Author, MongoAuthor> {

    @Override
    public MongoAuthor process(Author author) {
        MongoAuthor mongoAuthor = new MongoAuthor();
        mongoAuthor.setId(String.valueOf(author.getId()));
        mongoAuthor.setFullName(author.getFullName());
        return mongoAuthor;
    }

}
