package ru.otus.hw.dao;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.ColumnPositionMappingStrategyBuilder;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.dao.dto.QuestionDto;
import ru.otus.hw.domain.Question;
import ru.otus.hw.exceptions.QuestionReadException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class CsvQuestionDao implements QuestionDao {

    private final TestFileNameProvider fileNameProvider;

    /**
     * Найти все вопросы в 'questions.csv'
     */
    @Override
    public List<Question> findAll() {
        ClassLoader classLoader = getClass().getClassLoader();
        List<QuestionDto> questionDtoList;
        try (InputStream is = classLoader.getResourceAsStream(fileNameProvider.getTestFileName())) {
            if (is == null) {
                return null;
            }

            CSVParser parser = new CSVParserBuilder()
                    .withSeparator(';')
                    .build();

            CSVReader csvReader = new CSVReaderBuilder(new InputStreamReader(is))
                    .withSkipLines(1)
                    .withCSVParser(parser)
                    .build();

            ColumnPositionMappingStrategy<QuestionDto> strategy = new ColumnPositionMappingStrategyBuilder<QuestionDto>()
                    .build();
            strategy.setType(QuestionDto.class);

            questionDtoList = new CsvToBeanBuilder<QuestionDto>(csvReader)
                    .withMappingStrategy(strategy)
                    .build()
                    .parse();

        } catch (IOException e) {
            throw new QuestionReadException("Error: Problems with the file 'csv'", e);
        }

        List<Question> questionList = new ArrayList<>();
        questionDtoList.forEach(elt -> questionList.add(elt.toDomainObject()));

        return questionList;
    }

}
