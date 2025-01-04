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

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(properties = {"spring.shell.interactive.enabled=false"})
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

}