package ru.otus.hw.batch.writers;

import lombok.AllArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;
import ru.otus.hw.models.mongo.MongoAuthor;
import ru.otus.hw.repositories.mongo.MongoAuthorRepository;

@Component
@AllArgsConstructor
public class AuthorItemWriter implements ItemWriter<MongoAuthor> {

    private final MongoAuthorRepository mongoAuthorRepository;

    @Override
    public void write(Chunk<? extends MongoAuthor> chunk) {
        mongoAuthorRepository.saveAll(chunk);
    }

}
