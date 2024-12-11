package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import ru.otus.hw.config.AppProperties;
import ru.otus.hw.dao.CsvQuestionDao;
import ru.otus.hw.exceptions.QuestionReadException;

import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    // ioService был изначально прописан в spring-context.xml
    private final IOService ioService;

    @Override
    public void executeTest() {
        ioService.printLine("");
        ioService.printFormattedLine("Please answer the questions below%n");

        var provider = new AppProperties("questions.csv");
        var dao = new CsvQuestionDao(provider);
        var questionList = dao.findAll();

        if (questionList == null) {
            throw new QuestionReadException("Error: Problems with the file 'csv'");
        }

        var scanner = new Scanner(System.in); // Для чтения пользовательского ввода

        questionList.forEach(elt -> { // Перебираем все объекты Question
            var answersList = elt.answers(); // Ответы
            System.out.println(elt.text());
            AtomicInteger answerNumber = new AtomicInteger(1); // Счётчик
            answersList.forEach(elt1 -> {
                System.out.println(" " + answerNumber + ". " + elt1.text());
                answerNumber.getAndIncrement();
            });
            System.out.print("Enter the response number: ");

            int chosenAnswerIndex; // Считываем с консоли вариант ответа
            try {
                chosenAnswerIndex = Integer.parseInt(scanner.nextLine()) - 1;
            } catch (NumberFormatException ex) {
                chosenAnswerIndex = -1; // Если введён не Integer
            }

            if (chosenAnswerIndex >= 0 && chosenAnswerIndex < answersList.size()) { // Если введёно число не в пределах доступных ответов
                boolean isCorrect = answersList.get(chosenAnswerIndex).isCorrect();
                System.out.println(isCorrect ? "Correctly!\n" : "Wrong.\n");
            } else {
                System.out.println("Incorrect input!\n");
            }
        });

        scanner.close();
    }

}
