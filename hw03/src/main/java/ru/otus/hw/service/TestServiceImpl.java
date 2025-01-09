package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;

import java.util.List;

/**
 * Этот компонент вызывается из
 * {@link ru.otus.hw.service.TestRunnerServiceImpl}
 */
@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final LocalizedIOService ioService;

    private final QuestionDao questionDao;

    @Override
    public TestResult executeTestFor(Student student) {
        ioService.printLine("");
        ioService.printLineLocalized("TestService.answer.the.questions");
        ioService.printLine("");

        var questions = questionDao.findAll();
        var testResult = new TestResult(student);

        for (var question : questions) {
            ioService.printLine(question.text()); // Задаём вопрос
            var answersList = question.answers(); // Список ответов
            printPossibleAnswers(answersList); // Варианты ответов
            var chosenAnswerIndex = getChosenAnswerIndex(answersList); // Выбираем ответ
            var isAnswerValid = answersList.get(chosenAnswerIndex).isCorrect(); // Валидный ли ответ?

            // Правильный ли ответ?
            var correctOrWrongCode = isAnswerValid ? "TestService.correctly" : "TestService.wrong";
            ioService.printLineLocalized(correctOrWrongCode);

            testResult.applyAnswer(question, isAnswerValid);
        }
        return testResult;
    }

    /**
     * Выводим возможные варианты ответов
     */
    private void printPossibleAnswers(List<Answer> answersList) {
        for (int i = 0; i < answersList.size(); i++) {
            var answerElement = answersList.get(i);
            ioService.printLine(" " + (i + 1) + ". " + answerElement.text());
        }
    }

    /**
     * Получим выбранный ответ, или ошибку
     * Проверки заложены в 'ioService.readIntForRange'
     */
    private int getChosenAnswerIndex(List<Answer> answersList) {
        ioService.printLineLocalized("TestService.enter.response.number");
        var errMsg = ioService.getMessage("TestService.incorrect.input");
        int chosenAnswerIndex = ioService.readIntForRange(1, answersList.size(), errMsg);
        chosenAnswerIndex--; // Чтобы поиск в списке был корректен
        return chosenAnswerIndex;
    }

}