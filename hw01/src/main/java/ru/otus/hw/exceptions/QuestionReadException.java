package exceptions;

/**
 * Ошибка при чтении из csv
 */
public class QuestionReadException extends RuntimeException {
    public QuestionReadException(String message, Throwable ex) {
        super(message, ex);
    }

    public QuestionReadException(String message) {
        super(message);
    }
}
