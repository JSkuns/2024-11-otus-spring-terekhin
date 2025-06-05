package ru.otus.hw.batch.steps;

import lombok.AllArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import ru.otus.hw.batch.processors.GenreItemProcessor;
import ru.otus.hw.batch.readers.GenreItemReader;
import ru.otus.hw.batch.writers.GenreItemWriter;
import ru.otus.hw.models.h2.Genre;
import ru.otus.hw.models.mongo.MongoGenre;

@Component
@AllArgsConstructor
public class GenreStep {

    private static final int CHUNK_SIZE = 5;

    private final JobRepository jobRepository;

    private final GenreItemReader itemReader;

    private final GenreItemWriter itemWriter;

    private final GenreItemProcessor itemProcessor;

    private final PlatformTransactionManager platformTransactionManager;

    @Bean
    public Step migrateGenresStep() {
        return new StepBuilder("migrateGenreStep", jobRepository)
                .<Genre, MongoGenre>chunk(CHUNK_SIZE, platformTransactionManager)
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .build();
    }


}
