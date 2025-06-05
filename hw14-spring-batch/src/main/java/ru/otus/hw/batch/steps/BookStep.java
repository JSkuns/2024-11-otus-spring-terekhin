package ru.otus.hw.batch.steps;

import lombok.AllArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import ru.otus.hw.batch.processors.BookItemProcessor;
import ru.otus.hw.batch.readers.BookItemReader;
import ru.otus.hw.batch.writers.BookItemWriter;
import ru.otus.hw.models.h2.Book;
import ru.otus.hw.models.mongo.MongoBook;

@Component
@AllArgsConstructor
public class BookStep {

    private static final int CHUNK_SIZE = 3;

    private final JobRepository jobRepository;

    private final BookItemReader itemReader;

    private final BookItemWriter itemWriter;

    private final BookItemProcessor itemProcessor;

    private final PlatformTransactionManager platformTransactionManager;

    @Bean
    public Step migrateBooksStep() {
        return new StepBuilder("migrateBookStep", jobRepository)
                .<Book, MongoBook>chunk(CHUNK_SIZE, platformTransactionManager)
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .build();
    }


}
