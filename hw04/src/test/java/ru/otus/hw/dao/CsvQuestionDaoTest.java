package ru.otus.hw.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import ru.otus.hw.config.AppProperties;
import ru.otus.hw.exceptions.QuestionReadException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(properties = {"spring.shell.interactive.enabled=false"})
class CsvQuestionDaoTest {

    @MockitoBean
    private AppProperties appPropertiesMock;

    private CsvQuestionDao csvQuestionDao;

    @BeforeEach
    void setUp() {
        csvQuestionDao = new CsvQuestionDao(appPropertiesMock);
    }

    @Test
    void testCsvFileNotFound() {
        // Arrange
        Mockito.when(appPropertiesMock.getTestFileName()).thenReturn("test.csv");

        // Assert
        assertThrows(QuestionReadException.class, () -> csvQuestionDao.findAll());
    }

    @Test
    void testCsvFileWillFoundAndMethodReturnListOfQuestions() {
        // Arrange
        Mockito.when(appPropertiesMock.getTestFileName()).thenReturn("questions.csv");

        // Act
        var list = csvQuestionDao.findAll();

        // Assert
        assertNotNull(list);
    }

}