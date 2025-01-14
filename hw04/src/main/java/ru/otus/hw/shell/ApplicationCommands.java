package ru.otus.hw.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.command.annotation.Command;
import org.springframework.shell.standard.commands.Quit;
import ru.otus.hw.service.LocalizedIOService;
import ru.otus.hw.service.TestRunnerService;

@Command(group = "Applications Shell Commands")
public class ApplicationCommands implements Quit.Command {

    private final LocalizedIOService ioService;

    private final TestRunnerService testRunnerService;

    @Autowired
    public ApplicationCommands(LocalizedIOService ioService, TestRunnerService testRunnerService) {
        this.ioService = ioService;
        this.testRunnerService = testRunnerService;
    }

    /**
     * При вводе команды в shell - запустится основное тестирование
     */
    @Command(alias = "r", command = "run", description = "Run application.")
    public void runApp() {
        ioService.printLineLocalized("ApplicationCommands.starting.testing");
        testRunnerService.run();
    }

    /**
     * При вводе команды в shell - будет выполнен выход из программы
     */
    @Command(alias = {"q", "e"}, command = {"quit", "exit"}, description = "Exit the application.")
    public void quit() {
        ioService.printLineLocalized("ApplicationCommands.exit.testing.program");
        System.exit(0);
    }

}