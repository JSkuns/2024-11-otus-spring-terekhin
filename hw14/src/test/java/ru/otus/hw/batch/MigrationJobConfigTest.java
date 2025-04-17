package ru.otus.hw.batch;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.hw.batch.steps.AuthorStep;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBatchTest
@SpringBootTest(properties = {"spring.shell.interactive.enabled=false"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class MigrationJobConfigTest {

    private static final String COMPLETED = "COMPLETED";

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private JobRepositoryTestUtils jobRepositoryTestUtils;

    @BeforeEach
    void clearMetaData() {
        jobRepositoryTestUtils.removeJobExecutions();
    }

    @Test
//    @Timeout(value = 3, unit = TimeUnit.SECONDS)
    public void testMigrationJobCompletion() throws Exception {
        // Проверяем, что job загружена правильно
        Job job = jobLauncherTestUtils.getJob();
        assertThat(job).isNotNull()
                .extracting(Job::getName)
                .isEqualTo(MigrationJobConfig.MIGRATE_JOB);

        // Запускаем job
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();

        // Проверяем статус выполнения
        assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);

        // Проверяем количество шагов
        assertThat(jobExecution.getStepExecutions()).hasSize(4);

        // Проверяем статусы всех шагов
        assertThat(jobExecution.getStepExecutions())
                .as("Все шаги должны завершиться успешно.")
                .allMatch(step -> step.getExitStatus().getExitCode().equals(COMPLETED));
    }

    @Test
    public void testMigrationStep() {
        JobExecution jobExecution = jobLauncherTestUtils.launchStep(AuthorStep.MIGRATE_AUTHOR_STEP);
        assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());
    }

}