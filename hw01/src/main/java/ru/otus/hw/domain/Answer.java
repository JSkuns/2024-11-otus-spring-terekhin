package ru.otus.hw.domain;

/**
 * Класс - Ответ
 * text - собственно ответ
 * isCorrect - верный ли он
 */
public record Answer(String text, boolean isCorrect) {
}
