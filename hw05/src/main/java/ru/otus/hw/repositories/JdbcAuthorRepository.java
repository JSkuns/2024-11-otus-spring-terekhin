package ru.otus.hw.repositories;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.otus.hw.models.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class JdbcAuthorRepository implements AuthorRepository {

    private final NamedParameterJdbcTemplate jdbc;

    public JdbcAuthorRepository(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    /**
     * Получить всех авторов из БД
     */
    @Override
    public List<Author> findAll() {
        String sql = "SELECT * FROM authors";
        return jdbc.query(sql, new AuthorRowMapper());
    }

    /**
     * Найти автора по ID
     */
    @Override
    public Optional<Author> findById(long id) {
        Author author;
        String sql = "SELECT * FROM authors WHERE id = :id";
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("id", id);
        try {
            author = jdbc.queryForObject(sql, source, new AuthorRowMapper());
            Objects.requireNonNull(author);
        } catch (EmptyResultDataAccessException | NullPointerException ex) {
            return Optional.empty();
        }
        return Optional.of(author);
    }

    /**
     * Маппер
     */
    private static class AuthorRowMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet rs, int i) throws SQLException {
            Author author = new Author();
            author.setId(rs.getLong("id"));
            author.setFullName(rs.getString("full_name"));
            return author;
        }

    }

}