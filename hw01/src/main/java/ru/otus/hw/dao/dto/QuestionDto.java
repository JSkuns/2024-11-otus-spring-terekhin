package ru.otus.hw.dao.dto;

import com.opencsv.bean.CsvBindAndSplitByPosition;
import com.opencsv.bean.CsvBindByPosition;
import lombok.Data;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;

import java.util.ArrayList;
import java.util.List;

@Data
public class QuestionDto {

    // Первая позиция (0) csv - это сам вопрос
    @CsvBindByPosition(position = 0)
    private String text;

    // Вторая позиция (1) csv - это список ответов
    @CsvBindAndSplitByPosition(position = 1, collectionType = ArrayList.class, elementType = Answer.class,
            converter = AnswerCsvConverter.class, splitOn = "\\|")
    private List<Answer> answers;

    // Создание объекта - Вопрос
    public Question toDomainObject() {
        return new Question(text, answers);
    }

}
