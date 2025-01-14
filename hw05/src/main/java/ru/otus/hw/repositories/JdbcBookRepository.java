package ru.otus.hw.repositories;

import org.springframework.dao.EmptyResultDataAccessException;
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
import java.util.Objects;
import java.util.Optional;

@Repository
public class JdbcBookRepository implements BookRepository {

    private final NamedParameterJdbcTemplate jdbc;

    private List<Genre> allGenres;

    private List<Author> allAuthors;

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
        Book book;
        String sql = "SELECT * FROM books WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        findAllAuthorsAndGenres();
        try {
            book = jdbc.queryForObject(sql, params, new BookRowMapper(allGenres, allAuthors));
            Objects.requireNonNull(book);
        } catch (EmptyResultDataAccessException | NullPointerException ex) {
            return Optional.empty();
        }
        return Optional.of(book);
    }

    /**
     * Чтобы уменьшить кол-во запросов к БД, найдём всех авторов и все жанры
     */
    private void findAllAuthorsAndGenres() {
        allAuthors = new JdbcAuthorRepository(jdbc).findAll();
        allGenres = new JdbcGenreRepository(jdbc).findAll();
    }

    /**
     * Получить все книги из БД
     */
    @Override
    public List<Book> findAll() {
        String sql = "SELECT * FROM books";
        findAllAuthorsAndGenres();
        return jdbc.query(sql, new BookRowMapper(allGenres, allAuthors));
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
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        jdbc.update(sql, params);
    }

    /**
     * Добавление новой книги в БД
     */
    private Book insert(Book book) {
        var keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO books (title, author_id, genre_id) VALUES (:title, :author_id, :genre_id)";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("title", book.getTitle());
        params.addValue("author_id", book.getAuthor().getId());
        params.addValue("genre_id", book.getGenre().getId());
        jdbc.update(sql, params, keyHolder, new String[]{"id"});
        long generatedId;
        try {
            generatedId = Objects.requireNonNull(keyHolder.getKeyAs(Long.class));
        } catch (NullPointerException ex) {
            generatedId = 0; // id не сгенерировалось
        }
        book.setId(generatedId);
        return book;
    }

    /**
     * Пробуем изменить запись, либо получим ошибку
     */
    private Book update(Book book) {
        var targetId = book.getId(); // ID книги, которую хотим изменить
        try {
            if (findById(targetId).orElse(null) == null) {
                throw new EntityNotFoundException(null);
            }
            String sql = "UPDATE books SET title = :title, author_id = :author_id, genre_id = :genre_id WHERE id = :id";
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("title", book.getTitle());
            params.addValue("author_id", book.getAuthor().getId());
            params.addValue("genre_id", book.getGenre().getId());
            params.addValue("id", targetId);
            int countOfUpdatedBooks = jdbc.update(sql, params);
            if (countOfUpdatedBooks == 0) {
                throw new EntityNotFoundException(null);
            }
        } catch (EntityNotFoundException ex) {
            throw new EntityNotFoundException("The book with the id %d has not changed".formatted(targetId));
        }
        return book;
    }

    /**
     * Маппер
     */
    private static class BookRowMapper implements RowMapper<Book> {

        private final List<Genre> allGenres;

        private final List<Author> allAuthors;

        public BookRowMapper(List<Genre> allGenres, List<Author> allAuthors) {
            this.allGenres = allGenres;
            this.allAuthors = allAuthors;
        }

        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            Book book = new Book();
            book.setId(rs.getLong("id"));
            book.setTitle(rs.getString("title"));
            long authorId = rs.getLong("author_id");
            Optional<Author> author = allAuthors.stream()
                    .filter(elt -> elt.getId() == authorId)
                    .findFirst();
            book.setAuthor(author.orElse(null));
            long genreId = rs.getLong("genre_id");
            Optional<Genre> genre = allGenres.stream()
                    .filter(elt -> elt.getId() == genreId)
                    .findFirst();
            book.setGenre(genre.orElse(null));
            return book;
        }

    }

}