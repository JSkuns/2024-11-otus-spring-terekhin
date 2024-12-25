package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service // Этот компонент вызывается из TestRunnerServiceImpl.java
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final IOService ioService;

    private final QuestionDao questionDao;

    @Override
    public TestResult executeTestFor(Student student) {
        ioService.printLine("");
        ioService.printFormattedLine("Please answer the questions below%n");
        var questions = questionDao.findAll();
        var testResult = new TestResult(student);

        for (var question : questions) {
            var isAnswerValid = false; // Валидный ли ответ
            ioService.printLine(question.text()); // Задаём вопрос
            var answersList = question.answers(); // Списко ответов
            outputPossibleAnswers(answersList); // Выводим возможные варианты ответов
            ioService.printLine("Enter the response number: "); //
            int chosenAnswerIndex = getAnswer(); // Считываем с консоли вариант ответа

            if (chosenAnswerIndex >= 0 && chosenAnswerIndex < answersList.size()) {
                isAnswerValid = answersList.get(chosenAnswerIndex).isCorrect();
                ioService.printLine(isAnswerValid ? "Correctly!\n" : "Wrong.\n");
            } else {
                ioService.printLine("Incorrect input!\n");
            }

            testResult.applyAnswer(question, isAnswerValid);
        }
        return testResult;
    }

    private void outputPossibleAnswers(List<Answer> answersList) {
        AtomicInteger answerNumber = new AtomicInteger(1); // Счётчик
        answersList.forEach(answerElement -> {
            ioService.printLine(" " + answerNumber + ". " + answerElement.text()); // Выводим варианты ответов
            answerNumber.getAndIncrement(); // Увеличение счётчика
        });

    }

    private int getAnswer() {
        int chosenAnswerIndex;
        try {
            chosenAnswerIndex = Integer.parseInt(ioService.readString()) - 1;
        } catch (NumberFormatException ex) {
            chosenAnswerIndex = -1; // Если введён не Integer
        }
        return chosenAnswerIndex;
    }

}
