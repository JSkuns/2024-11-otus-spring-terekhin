package ru.otus.hw.batch.steps;

import lombok.AllArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import ru.otus.hw.batch.processors.AuthorItemProcessor;
import ru.otus.hw.batch.readers.AuthorItemReader;
import ru.otus.hw.batch.writers.AuthorItemWriter;
import ru.otus.hw.models.h2.Author;
import ru.otus.hw.models.mongo.MongoAuthor;

@Component
@AllArgsConstructor
public class AuthorStep {

    private static final int CHUNK_SIZE = 2;

    private final JobRepository jobRepository;

    private final AuthorItemReader itemReader;

    private final AuthorItemWriter itemWriter;

    private final AuthorItemProcessor itemProcessor;

    private final PlatformTransactionManager platformTransactionManager;

    @Bean
    public Step migrateAuthorsStep() {
        return new StepBuilder("migrateAuthorStep", jobRepository)
                .<Author, MongoAuthor>chunk(CHUNK_SIZE, platformTransactionManager)
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .build();
    }


}
