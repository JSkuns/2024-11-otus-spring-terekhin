package dao;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.ColumnPositionMappingStrategyBuilder;
import com.opencsv.bean.CsvToBeanBuilder;
import config.TestFileNameProvider;
import dao.dto.QuestionDto;
import domain.Question;
import exceptions.QuestionReadException;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class CsvQuestionDao implements QuestionDao {

    private final TestFileNameProvider fileNameProvider;
    private static final String ERROR_MSG = "Error: Problems with the file %s";
    private static final char SEPARATOR_SEMICOLON = ';';

    /**
     * Найти все вопросы в 'questions.csv'
     */
    @Override
    public List<Question> findAll() {
        ClassLoader classLoader = getClassLoader();
        List<QuestionDto> questionDtoList = null;
        try (InputStream is = classLoader.getResourceAsStream(fileNameProvider.getTestFileName())) {
            if (is == null) {
                throwQuestionReadException(fileNameProvider, null);
            }
            CSVParser parser = getCSVParser();
            CSVReader csvReader = getCSVReader(parser, is);
            ColumnPositionMappingStrategy<QuestionDto> strategy = getStrategy();
            strategy.setType(QuestionDto.class);
            questionDtoList = getQuestionDtoList(csvReader, strategy);

        } catch (IOException ex) {
            throwQuestionReadException(fileNameProvider, ex);
        }

        List<Question> questionList = new ArrayList<>();
        questionDtoList.forEach(elt -> questionList.add(elt.toDomainObject()));

        return questionList;
    }

    /**
     * Получаем ClassLoader
     */
    private ClassLoader getClassLoader() {
        return getClass().getClassLoader();
    }

    /**
     * Выбрасываем QuestionReadException
     */
    private void throwQuestionReadException(TestFileNameProvider fileNameProvider, Exception ex) {
        var errMsg = String.format(ERROR_MSG, fileNameProvider.getTestFileName());
        throw new QuestionReadException(errMsg, ex);
    }

    /**
     * Получаем CSVParser
     */
    private CSVParser getCSVParser() {
        return new CSVParserBuilder()
                .withSeparator(SEPARATOR_SEMICOLON)
                .build();
    }

    /**
     * Получаем CSVReader
     */
    private CSVReader getCSVReader(CSVParser parser, InputStream is) {
        return new CSVReaderBuilder(new InputStreamReader(is))
                .withSkipLines(1)
                .withCSVParser(parser)
                .build();
    }

    /**
     * Получаем ColumnPositionMappingStrategy
     */
    private ColumnPositionMappingStrategy<QuestionDto> getStrategy() {
        return new ColumnPositionMappingStrategyBuilder<QuestionDto>()
                .build();
    }

    /**
     * Получаем список объектов QuestionDto из csv файла
     */
    private List<QuestionDto> getQuestionDtoList(
            CSVReader csvReader,
            ColumnPositionMappingStrategy<QuestionDto> strategy) {
        return new CsvToBeanBuilder<QuestionDto>(csvReader)
                .withMappingStrategy(strategy)
                .build()
                .parse();
    }

}
