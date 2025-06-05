package ru.otus.hw.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.exceptions.QuestionReadException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class CsvQuestionDaoTest {

    private TestFileNameProvider fileNameProviderMock;
    private CsvQuestionDao dao;

    @BeforeEach
    void setUp() {
        fileNameProviderMock = Mockito.mock(TestFileNameProvider.class);
        dao = new CsvQuestionDao(fileNameProviderMock);
    }

    @Test
    void testCsvFileNotFound() {
        when(fileNameProviderMock.getTestFileName()).thenReturn("nonexistent.csv");

        assertThrows(QuestionReadException.class, () -> dao.findAll());
    }

}