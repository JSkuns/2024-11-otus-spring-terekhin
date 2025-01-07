package ru.otus.hw.dao;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.ColumnPositionMappingStrategyBuilder;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.dao.dto.QuestionDto;
import ru.otus.hw.domain.Question;
import ru.otus.hw.exceptions.QuestionReadException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Этот компонент вызывается из TestServiceImpl.java
 * {@link ru.otus.hw.service.TestServiceImpl}
 */
@RequiredArgsConstructor
@Component
public class CsvQuestionDao implements QuestionDao {
    private final TestFileNameProvider fileNameProvider;

    /**
     * Найти все вопросы в 'questions.csv'
     */
    @Override
    public List<Question> findAll() {
        ClassLoader classLoader = getClassLoader();
        List<QuestionDto> questionDtoList = null;
        try (
                InputStream is = Objects.requireNonNull(classLoader.getResourceAsStream(fileNameProvider.getTestFileName()))
        ) {
            CSVParser parser = getCSVParser();
            CSVReader csvReader = getCSVReader(parser, is);
            ColumnPositionMappingStrategy<QuestionDto> strategy = getStrategy();
            strategy.setType(QuestionDto.class);
            questionDtoList = getQuestionDtoList(csvReader, strategy);

        } catch (NullPointerException | IOException ex) {
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
        var errMsg = String.format("Error: Problems with the file %s", fileNameProvider.getTestFileName());
        throw new QuestionReadException(errMsg, ex);
    }

    /**
     * Получаем CSVParser
     */
    private CSVParser getCSVParser() {
        return new CSVParserBuilder()
                .withSeparator(';')
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
