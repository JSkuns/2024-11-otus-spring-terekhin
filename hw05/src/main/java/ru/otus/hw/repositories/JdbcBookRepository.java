package ru.otus.hw.repositories;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class JdbcBookRepository implements BookRepository {

    private static AuthorRepository authorRepository;
    private static GenreRepository genreRepository;
    private NamedParameterJdbcOperations jdbc;

    public JdbcBookRepository(AuthorRepository authorRepository, GenreRepository genreRepository, NamedParameterJdbcOperations jdbc) {
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
        this.jdbc = jdbc;
    }

    /**
     * Ищем одну книгу по ID
     * _
     * Используется в
     * {@link ru.otus.hw.services.BookServiceImpl}
     * {@link ru.otus.hw.commands.BookCommands}
     */
    @Override
    public Optional<Book> findById(long id) {
        String sql = "SELECT * FROM books WHERE id = :id";

        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("id", id);

        Book book = jdbc.queryForObject(sql, source, new BookRowMapper());

        return Optional.of(Objects.requireNonNull(book)); // NPE обрабатывается в BookCommands
    }

    /**
     * Ищем все книги.
     * _
     * Используется в
     * {@link ru.otus.hw.services.BookServiceImpl}
     * {@link ru.otus.hw.commands.BookCommands}
     */
    @Override
    public List<Book> findAll() {
        String sql = "SELECT * FROM books";

        return jdbc.query(
                sql,
                new BookRowMapper());
    }

    /**
     * Сохраняем или изменяем книгу (В зависимости от ID объекта Book).
     * _
     * Используется в
     * {@link ru.otus.hw.services.BookServiceImpl}
     * {@link ru.otus.hw.commands.BookCommands}
     */
    @Override
    public Book save(Book book) {
        if (book.getId() == 0) {
            return insert(book);
        }
        return update(book);
    }

    /**
     * Удалить какую-либо книгу по Id.
     * _
     * Используется в
     * {@link ru.otus.hw.services.BookServiceImpl}
     * {@link ru.otus.hw.commands.BookCommands}
     */
    @Override
    public void deleteById(long id) {
        try {
            String sql = "DELETE FROM books WHERE id = :id";

            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("id", id);

            jdbc.update(sql, params);
        } catch (RuntimeException ex) {
            throw new RuntimeException("The book with the id %d has not been deleted".formatted(id));
        }
    }

    /**
     * Добавление новой книги в БД
     */
    private Book insert(Book book) {
        var keyHolder = new GeneratedKeyHolder();

        String sql = "INSERT INTO books (title, author_id, genre_id) VALUES (:title, :author_id, :genre_id)";

        Optional<Book> insertedBook;
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("title", book.getTitle());
        params.addValue("author_id", book.getAuthor().getId());
        params.addValue("genre_id", book.getGenre().getId());

        jdbc.update(sql, params, keyHolder, new String[]{"id"});

        insertedBook = findById(Objects.requireNonNull(keyHolder.getKey()).longValue()); // Не находит новую запись, почему ???

        return insertedBook.orElse(null);
    }

    /**
     * Пробуем изменить запись, либо получим ошибку
     */
    private Book update(Book book) {
        // Найдём все Id книг из БД
        var booksIds = findAll();
        // ID книги, которую хотим изменить
        var targetId = book.getId();

        Book updatedBook;

        try {
            // Если нет соответствий в БД
            if (booksIds.stream().noneMatch(elt -> elt.getId() == targetId)) {
                throw new NullPointerException();
            }

            String sql = "UPDATE books SET title = :title, author_id = :author_id, genre_id = :genre_id WHERE id = :id";

            MapSqlParameterSource source = new MapSqlParameterSource();
            source.addValue("title", book.getTitle());
            source.addValue("author_id", book.getAuthor().getId());
            source.addValue("genre_id", book.getGenre().getId());
            source.addValue("id", targetId);

            // Делаем запрос на изменение
            jdbc.update(sql, source);

            // Получаем запись из БД, чтобы проверить, прошли ли изменения
            updatedBook = findById(targetId).orElseThrow(NullPointerException::new); // Возвращает старую запись, почему??? Т.е. не происходит обновление.

            // Проверим, обновилась ли запись?
            if (book.getId() != updatedBook.getId()
                    || !book.getTitle().equals(updatedBook.getTitle())
                    || book.getGenre().getId() != updatedBook.getGenre().getId()
                    || book.getAuthor().getId() != updatedBook.getAuthor().getId()) {
                throw new NullPointerException();
            }

        } catch (NullPointerException ex) {
            throw new EntityNotFoundException("The book with the id %d has not changed".formatted(targetId));
        }

        // Вернём новую запись из БД
        return updatedBook;
    }

    /**
     * Mapper
     */
    private static class BookRowMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            // Создаём книгу
            Book book = new Book();

            // Заполняем поля
            book.setId(rs.getLong("ID"));
            // Автор
            Optional<Author> author = authorRepository.findById(rs.getLong("ID"));
            book.setAuthor(author.orElse(null));
            // Жанр
            Optional<Genre> genre = genreRepository.findById(rs.getLong("ID"));
            book.setGenre(genre.orElse(null));
            // Заголовок
            book.setTitle(rs.getString("TITLE"));

            // Вернём книгу
            return book;
        }

    }

}