package ru.otus.hw.batch;

import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.hw.batch.steps.AuthorStep;
import ru.otus.hw.batch.steps.BookStep;
import ru.otus.hw.batch.steps.CommentStep;
import ru.otus.hw.batch.steps.GenreStep;

@Configuration
@AllArgsConstructor
public class MigrationJobConfig {

    protected final static String MIGRATE_JOB = "migrateJob";

    private final AuthorStep authorStep;

    private final BookStep bookStep;

    private final GenreStep genreStep;

    private final CommentStep commentStep;

    private final JobRepository jobRepository;

    @Bean
    public Job migrationJob() {
        return new JobBuilder(MIGRATE_JOB, jobRepository)
                .start(authorStep.migrateAuthorsStep())
                .next(genreStep.migrateGenresStep())
                .next(bookStep.migrateBooksStep())
                .next(commentStep.migrateCommentsStep())
                .build();
    }

}
