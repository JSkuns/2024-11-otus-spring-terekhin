package ru.otus.hw.commands;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@RequiredArgsConstructor
@ShellComponent
@Slf4j
public class MigrationCommands {

    private final JobLauncher jobLauncher;

    private final ConfigurableApplicationContext context;

    /**
     * Запуск миграции данных.
     */
    @ShellMethod(value = "Запустить миграцию", key = {"start-migration", "sm"})
    public void startMigration() {
        Job job = context.getBean("migrationJob", Job.class);
        try {
            JobExecution execution = jobLauncher.run(job, new JobParameters());
            logExecution(execution);
        } catch (JobExecutionException ex) {
            handleJobFailure(ex);
        }
    }

    /**
     * Логирование статуса выполнения задания.
     */
    private void logExecution(JobExecution execution) {
        if (execution != null) {
            log.info("Task completion status: " + execution.getStatus());
        }
    }

    /**
     * Обработка ошибок выполнения заданий.
     */
    private void handleJobFailure(JobExecutionException exception) {
        log.error(exception.getMessage());
    }

}