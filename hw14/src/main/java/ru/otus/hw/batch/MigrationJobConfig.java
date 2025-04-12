package ru.otus.hw.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.hw.batch.steps.AuthorStep;
import ru.otus.hw.batch.steps.BookStep;
import ru.otus.hw.batch.steps.CommentStep;
import ru.otus.hw.batch.steps.GenreStep;

@Configuration
public class MigrationJobConfig {

    @Autowired
    private AuthorStep authorStep;

    @Autowired
    private BookStep bookStep;

    @Autowired
    private GenreStep genreStep;

    @Autowired
    private CommentStep commentStep;

    @Autowired
    private JobRepository jobRepository;

    @Bean
    public Job migrationJob() {
        return new JobBuilder("migrateJob", jobRepository)
                .start(authorStep.migrateAuthorsStep())
                .next(genreStep.migrateGenresStep())
                .next(bookStep.migrateBooksStep())
                .next(commentStep.migrateCommentsStep())
                .build();
    }

}
