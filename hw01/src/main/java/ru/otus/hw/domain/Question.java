package ru.otus.hw.domain;

import java.util.List;

/**
 * Объект вопроса
 * text - текст вопроса
 * answers - список ответов
 */
public record Question(String text, List<Answer> answers) {
}
