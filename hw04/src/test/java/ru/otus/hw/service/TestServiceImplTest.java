package ru.otus.hw.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(properties = {"spring.shell.interactive.enabled=false"})
class TestServiceImplTest {

    private TestServiceImpl service;
    private Student student;
    private Question question;

    @MockitoBean
    private LocalizedIOService ioServiceMock;

    @MockitoBean
    private QuestionDao questionDaoMock;

    @BeforeEach
    void setup() {
        student = new Student("John", "Doe");
        List<Answer> answersList = List.of(
                new Answer("answer1", true),
                new Answer("answer2", false)
        );
        question = new Question("question1", answersList);

        service = new TestServiceImpl(ioServiceMock, questionDaoMock);
    }

    @Test
    void testExpectedCorrectAnswer() {
        // Act
        TestResult result = service.executeTestFor(student);
        result.applyAnswer(question, true);

        // Assert
        assertEquals(1, result.getRightAnswersCount());
    }

}