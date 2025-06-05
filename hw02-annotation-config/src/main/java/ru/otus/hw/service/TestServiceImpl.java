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
            ioService.printLine(question.text()); // Задаём вопрос
            var answersList = question.answers(); // Список ответов
            outputPossibleAnswers(answersList); // Варианты ответов
            var chosenAnswerIndex = getChosenAnswerIndex(answersList); // Выбираем ответ
            var isAnswerValid = answersList.get(chosenAnswerIndex).isCorrect(); // Валидный ли ответ?
            ioService.printLine(isAnswerValid ? "Correctly!\n" : "Wrong!\n"); // Правильно?

            testResult.applyAnswer(question, isAnswerValid);
        }
        return testResult;
    }

    /**
     * Выводим возможные варианты ответов
     */
    private void outputPossibleAnswers(List<Answer> answersList) {
        AtomicInteger answerNumber = new AtomicInteger(1); // Счётчик
        answersList.forEach(answerElement -> {
            ioService.printLine(" " + answerNumber + ". " + answerElement.text()); // Выводим варианты ответов
            answerNumber.getAndIncrement(); // Увеличение счётчика
        });
    }

    /**
     * Получим выбранный ответ, или ошибку
     * Проверки заложены в 'ioService.readIntForRange'
     */
    private int getChosenAnswerIndex(List<Answer> answersList) {
        ioService.printLine("Enter the response number: ");
        int chosenAnswerIndex = ioService.readIntForRange(1, answersList.size(), "Incorrect input!");
        chosenAnswerIndex--; // Чтобы поиск в списке был корректен
        return chosenAnswerIndex;
    }

}
