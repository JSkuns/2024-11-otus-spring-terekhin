package ru.otus.hw.batch;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.hw.batch.steps.AuthorStep;
import ru.otus.hw.batch.steps.BookStep;
import ru.otus.hw.batch.steps.CommentStep;
import ru.otus.hw.batch.steps.GenreStep;

@Slf4j
@Configuration
@AllArgsConstructor
public class MigrationJobConfig {

    protected static final String MIGRATE_JOB = "migrateJob";

    private final AuthorStep authorStep;

    private final BookStep bookStep;

    private final GenreStep genreStep;

    private final CommentStep commentStep;

    private final JobRepository jobRepository;

    @Bean
    public Job migrationJob() {
        return new JobBuilder(MIGRATE_JOB, jobRepository)
                .incrementer(new RunIdIncrementer())
                .flow(authorStep.migrateAuthorsStep())
                .next(genreStep.migrateGenresStep())
                .next(bookStep.migrateBooksStep())
                .next(commentStep.migrateCommentsStep())
                .end()
                .listener(getJobExecutionListener())
                .build();
    }

    private JobExecutionListener getJobExecutionListener() {
        return new JobExecutionListener() {
            @Override
            public void beforeJob(JobExecution jobExecution) {
                log.info("*** Start job ***");
                JobExecutionListener.super.beforeJob(jobExecution);
            }

            @Override
            public void afterJob(JobExecution jobExecution) {
                log.info("*** Finish job ***");
                JobExecutionListener.super.afterJob(jobExecution);
            }
        };
    }

}
