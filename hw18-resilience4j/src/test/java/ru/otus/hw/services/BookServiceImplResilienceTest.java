package ru.otus.hw.services;

import io.github.resilience4j.timelimiter.TimeLimiter;
import io.github.resilience4j.timelimiter.TimeLimiterRegistry;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import ru.otus.hw.dto.mappers.impl.BookDtoMapper;
import ru.otus.hw.dto.models.book.BookDto;
import ru.otus.hw.models.Book;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.services.impl.BookServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@Import({BookServiceImpl.class, BookDtoMapper.class})
@SpringBootTest
public class BookServiceImplResilienceTest {

    private static final long TEST_BOOK_ID = 1L;

    @Autowired
    private BookService bookService;

    @MockitoBean
    private BookRepository bookRepository;

    @MockitoBean
    private BookDtoMapper bookDtoMapper;

    @MockitoBean
    private TimeLimiterRegistry timeLimiterRegistry;

    @Test
    void testFindByIdSuccess() {
        long validBookId = 1L;

        // Создание фиктивного экземпляра книги
        Book fakeBook = new Book();
        fakeBook.setId(validBookId);
        fakeBook.setTitle("The Great Gatsby");

        // Моком книгу возвращаемую из репозитория
        when(bookRepository.findById(validBookId)).thenReturn(Optional.of(fakeBook));

        // Ожидаемый DTO объект книги
        BookDto expectedBookDto = new BookDto(validBookId, "The Great Gatsby", null, null);
        when(bookDtoMapper.toDto(fakeBook)).thenReturn(expectedBookDto);

        // Запускаем метод сервиса
        BookDto result = bookService.findById(validBookId);

        assertEquals(expectedBookDto, result);
    }

    @Test
    void findById_WhenRepositoryThrowsException_ShouldUseFallback() {
        // Arrange
        Mockito.doThrow(new RuntimeException("Database error"))
                .when(bookRepository)
                .findById(TEST_BOOK_ID);

        // Act
        BookDto result = bookService.findById(TEST_BOOK_ID);

        // Assert
        assertEquals(-1L, result.getId());
        assertEquals("No title", result.getTitle());
        assertEquals("Nobody", result.getAuthor().getFullName());
        assertEquals("No genre", result.getGenre().getName());
    }

    @Test
    void findById_WhenCircuitBreakerOpen_ShouldUseFallback() {
        // Act
        BookDto result = bookService.findById(TEST_BOOK_ID);

        // Assert
        assertEquals(-1L, result.getId());
        assertEquals("No title", result.getTitle());
        assertEquals("Nobody", result.getAuthor().getFullName());
        assertEquals("No genre", result.getGenre().getName());
    }

    @Test
    void findById_WhenTimeout_ShouldUseFallback() {
        // Arrange
        TimeLimiter timeLimiter = mock(TimeLimiter.class);
        when(timeLimiterRegistry.timeLimiter("bookTL")).thenReturn(timeLimiter);
        when(bookRepository.findById(TEST_BOOK_ID)).thenAnswer(invocation -> {
            Thread.sleep(500); // Имитируем долгий вызов
            return Optional.empty();
        });

        // Act
        BookDto result = bookService.findById(TEST_BOOK_ID);

        // Assert
        assertEquals(-1L, result.getId());
        assertEquals("No title", result.getTitle());
        assertEquals("Nobody", result.getAuthor().getFullName());
        assertEquals("No genre", result.getGenre().getName());
    }

    @Test
    void findById_WhenMaxRetriesExceeded_ShouldUseFallback() {
        // Arrange
        when(bookRepository.findById(TEST_BOOK_ID)).thenThrow(new RuntimeException("Temporary error"));

        // Act
        BookDto result = bookService.findById(TEST_BOOK_ID);

        // Assert
        assertEquals(-1L, result.getId());
        assertEquals("No title", result.getTitle());
        assertEquals("Nobody", result.getAuthor().getFullName());
        assertEquals("No genre", result.getGenre().getName());

        // Проверяем количество вызовов (первая попытка + две попытки ретрай)
        bookService.findById(TEST_BOOK_ID);
        bookService.findById(TEST_BOOK_ID);
        verify(bookRepository, times(3)).findById(TEST_BOOK_ID); // Проверяем 3 попытки
    }

}
