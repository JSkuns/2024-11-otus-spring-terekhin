package dao;

import domain.Question;

import java.util.List;

public interface QuestionDao {
    List<Question> findAll();
}
