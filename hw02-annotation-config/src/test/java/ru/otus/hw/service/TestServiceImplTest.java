package ru.otus.hw.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestServiceImplTest {

    private TestServiceImpl service;
    private Student student;
    private Question question;

    @BeforeEach
    void setup() {
        student = new Student("John", "Doe");
        List<Answer> answersList = List.of(
                new Answer("answer1", true),
                new Answer("answer2", false)
        );
        question = new Question("question1", answersList);

        IOService ioServiceMock = Mockito.mock(IOService.class);
        QuestionDao daoMock = Mockito.mock(QuestionDao.class);

        service = new TestServiceImpl(ioServiceMock, daoMock);
    }

    @Test
    void expectedCorrectAnswer() {
        // Act
        TestResult result = service.executeTestFor(student);
        result.applyAnswer(question, true);

        // Assert
        assertEquals(1, result.getRightAnswersCount());
    }

}