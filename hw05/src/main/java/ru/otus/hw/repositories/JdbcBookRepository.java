package ru.otus.hw.repositories;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcBookRepository implements BookRepository {

    private final NamedParameterJdbcTemplate jdbc;

    /**
     * Конструктор
     */
    public JdbcBookRepository(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    /**
     * Найти книгу по ID
     */
    @Override
    public Optional<Book> findById(long id) {
        String sql = "SELECT * " +
                "FROM books b " +
                "JOIN authors a ON b.author_id = a.id " +
                "JOIN genres g ON b.genre_id = g.id " +
                "WHERE b.id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", id);
        return jdbc.query(sql, params, new BookRowMapper()).stream().findFirst();
    }

    /**
     * Получить все книги из БД
     */
    @Override
    public List<Book> findAll() {
        String sql = "SELECT * " +
                "FROM books b " +
                "JOIN authors a ON b.author_id = a.id " +
                "JOIN genres g ON b.genre_id = g.id";
        return jdbc.query(sql, new BookRowMapper());
    }

    /**
     * Сохраняем или изменяем книгу (В зависимости от ID объекта Book).
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
     */
    @Override
    public void deleteById(long id) {
        String sql = "DELETE FROM books WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", id);
        jdbc.update(sql, params);
    }

    /**
     * Добавление новой книги в БД
     */
    private Book insert(Book book) {
        var keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO books (title, author_id, genre_id) VALUES (:title, :author_id, :genre_id)";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("title", book.getTitle())
                .addValue("author_id", book.getAuthor().getId())
                .addValue("genre_id", book.getGenre().getId());
        jdbc.update(sql, params, keyHolder, new String[]{"id"});
        var generatedId = keyHolder.getKeyAs(Long.class);
        book.setId(generatedId != null ? generatedId : 0);
        return book;
    }

    /**
     * Пробуем изменить запись, либо получим ошибку
     */
    private Book update(Book book) {
        String sql = "UPDATE books SET title = :title, author_id = :author_id, genre_id = :genre_id WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("title", book.getTitle())
                .addValue("author_id", book.getAuthor().getId())
                .addValue("genre_id", book.getGenre().getId())
                .addValue("id", book.getId());
        int countOfUpdatedBooks = jdbc.update(sql, params);
        if (countOfUpdatedBooks == 0) {
            throw new EntityNotFoundException("Couldn't update the book");
        }
        return book;
    }

    /**
     * Маппер
     */
    private static class BookRowMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            Genre genre = new Genre();
            genre.setId(rs.getLong("genres.id"));
            genre.setName(rs.getString("genres.name"));
            Author author = new Author();
            author.setId(rs.getLong("authors.id"));
            author.setFullName(rs.getString("authors.full_name"));
            Book book = new Book();
            book.setId(rs.getLong("books.id"));
            book.setTitle(rs.getString("books.title"));
            book.setAuthor(author);
            book.setGenre(genre);
            return book;
        }

    }

}