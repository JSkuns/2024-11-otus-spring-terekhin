package ru.otus.hw.batch;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.hw.batch.steps.AuthorStep;
import ru.otus.hw.batch.steps.BookStep;
import ru.otus.hw.batch.steps.CommentStep;
import ru.otus.hw.batch.steps.GenreStep;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBatchTest
@SpringBootTest(classes = {AuthorStep.class, GenreStep.class, BookStep.class, CommentStep.class})
public class MigrationJobConfigTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private JobRepository jobRepository;

    @Test
    public void testMigrationJobCompletion() throws Exception {
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(new JobParameters());

        assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus(),
                "The job did not complete successfully.");
    }

}