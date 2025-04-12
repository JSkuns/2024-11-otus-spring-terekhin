package ru.otus.hw.batch.writers;

import lombok.AllArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;
import ru.otus.hw.models.mongo.MongoComment;
import ru.otus.hw.repositories.mongo.MongoCommentRepository;

@Component
@AllArgsConstructor
public class CommentItemWriter implements ItemWriter<MongoComment> {

    private final MongoCommentRepository mongoCommentRepository;

    @Override
    public void write(Chunk<? extends MongoComment> chunk) {
        mongoCommentRepository.saveAll(chunk);
    }

}
