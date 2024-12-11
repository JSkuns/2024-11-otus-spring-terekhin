package ru.otus.hw.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.PrintStream;

import static org.mockito.Mockito.verify;

class StreamsIOServiceTest {

    @Test
    void printLine() {
        // Создаем мок объекта PrintStream
        PrintStream mockPrintStream = Mockito.mock(PrintStream.class);

        // Создаем экземпляр сервиса с использованием мока
        IOService service = new StreamsIOService(mockPrintStream);

        // Вызываем метод printLine
        String message = "Hello, World!";
        service.printLine(message);

        // Проверяем, что метод println был вызван с правильным аргументом
        verify(mockPrintStream).println(message);
    }

    @Test
    void printFormattedLine() {
        // Создаем мок объекта PrintStream
        PrintStream mockPrintStream = Mockito.mock(PrintStream.class);

        // Создаем экземпляр сервиса с использованием мока
        IOService service = new StreamsIOService(mockPrintStream);

        // Вызываем метод printFormattedLine
        String format = "The number is %d";
        int value = 42;
        service.printFormattedLine(format, value);

        // Проверяем, что метод printf был вызван с правильными аргументами
        verify(mockPrintStream).printf(format + "%n", value);
    }

}