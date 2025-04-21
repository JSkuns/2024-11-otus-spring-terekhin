package ru.otus.hw.batch.writers;

import lombok.AllArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;
import ru.otus.hw.models.mongo.MongoBook;
import ru.otus.hw.repositories.mongo.MongoBookRepository;

@Component
@AllArgsConstructor
public class BookItemWriter implements ItemWriter<MongoBook> {

    private final MongoBookRepository mongoBookRepository;

    @Override
    public void write(Chunk<? extends MongoBook> chunk) {
        mongoBookRepository.saveAll(chunk);
    }

}
