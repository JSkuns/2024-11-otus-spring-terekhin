package hw.dao;

import config.TestFileNameProvider;
import dao.CsvQuestionDao;
import exceptions.QuestionReadException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CsvQuestionDaoTest {

    private CsvQuestionDao target;

    private static final String FILE_NAME_TEST = "test.csv";

    @BeforeEach
    void setUp() {
        TestFileNameProvider fileNameProviderMock = mock(TestFileNameProvider.class);

        when(fileNameProviderMock.getTestFileName()).thenReturn(FILE_NAME_TEST);

        target = new CsvQuestionDao(fileNameProviderMock);
    }

    @Test
    void shouldThrowExceptionWhenNoQuestionsInFile() {
        // Then
        assertThrows(QuestionReadException.class, () -> target.findAll());
    }


}