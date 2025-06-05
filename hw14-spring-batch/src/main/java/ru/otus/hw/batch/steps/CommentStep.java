package ru.otus.hw.batch.steps;

import lombok.AllArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import ru.otus.hw.batch.processors.CommentItemProcessor;
import ru.otus.hw.batch.readers.CommentItemReader;
import ru.otus.hw.batch.writers.CommentItemWriter;
import ru.otus.hw.models.h2.Comment;
import ru.otus.hw.models.mongo.MongoComment;

@Component
@AllArgsConstructor
public class CommentStep {

    private static final int CHUNK_SIZE = 4;

    private final JobRepository jobRepository;

    private final CommentItemReader itemReader;

    private final CommentItemWriter itemWriter;

    private final CommentItemProcessor itemProcessor;

    private final PlatformTransactionManager platformTransactionManager;

    @Bean
    public Step migrateCommentsStep() {
        return new StepBuilder("migrateCommentStep", jobRepository)
                .<Comment, MongoComment>chunk(CHUNK_SIZE, platformTransactionManager)
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .build();
    }

}
