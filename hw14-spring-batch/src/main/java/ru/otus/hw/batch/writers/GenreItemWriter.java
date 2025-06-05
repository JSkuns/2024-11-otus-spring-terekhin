package ru.otus.hw.batch.writers;

import lombok.AllArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;
import ru.otus.hw.models.mongo.MongoGenre;
import ru.otus.hw.repositories.mongo.MongoGenreRepository;

@Component
@AllArgsConstructor
public class GenreItemWriter implements ItemWriter<MongoGenre> {

    private final MongoGenreRepository mongoGenreRepository;

    @Override
    public void write(Chunk<? extends MongoGenre> chunk) {
        mongoGenreRepository.saveAll(chunk);
    }

}
