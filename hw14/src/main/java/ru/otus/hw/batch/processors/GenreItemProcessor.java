package ru.otus.hw.batch.processors;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import ru.otus.hw.models.h2.Genre;
import ru.otus.hw.models.mongo.MongoGenre;

@Component
public class GenreItemProcessor implements ItemProcessor<Genre, MongoGenre> {

    @Override
    public MongoGenre process(Genre genre) {
        MongoGenre mongoGenre = new MongoGenre();
        mongoGenre.setId(String.valueOf(genre.getId()));
        mongoGenre.setName(genre.getName());
        return mongoGenre;
    }

}
