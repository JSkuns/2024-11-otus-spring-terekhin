package ru.otus.hw.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.hw.config.AppProperties;
import ru.otus.hw.exceptions.QuestionReadException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = CsvQuestionDao.class)
@ExtendWith(SpringExtension.class)
class CsvQuestionDaoTest {

    @MockBean
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