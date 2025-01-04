package ru.otus.hw.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.ExitRequest;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.commands.Quit;
import ru.otus.hw.service.LocalizedIOService;

@ShellComponent(value = "Applications Shell Commands")
public class ApplicationCommands implements Quit.Command {

    private final LocalizedIOService ioService;

    @Autowired
    public ApplicationCommands(LocalizedIOService ioService) {
        this.ioService = ioService;
    }

    /**
     * При вводе команды в shell - запустится основное тестирование
     */
    @ShellMethod(value = "Run application.", key = {"r", "run"})
    public void runApp() {
        ioService.printLineLocalized("ApplicationCommands.starting.testing");
        throw new ExitRequest();
    }

    /**
     * При вводе команды в shell - будет выполнен выход из программы
     */
    @ShellMethod(value = "Exit the application.", key = {"q", "e", "quit", "exit"})
    public void quit() {
        ioService.printLineLocalized("ApplicationCommands.exit.testing.program");
        System.exit(0);
    }

}
