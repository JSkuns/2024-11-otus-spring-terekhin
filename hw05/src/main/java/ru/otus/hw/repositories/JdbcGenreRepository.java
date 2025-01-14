package ru.otus.hw.repositories;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.otus.hw.models.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class JdbcGenreRepository implements GenreRepository {

    private final NamedParameterJdbcTemplate jdbc;

    public JdbcGenreRepository(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    /**
     * Получить все жанры из БД
     */
    @Override
    public List<Genre> findAll() {
        String sql = "SELECT * FROM genres";
        return jdbc.query(sql, new GnreRowMapper());
    }

    /**
     * Найти жанр по ID
     */
    @Override
    public Optional<Genre> findById(long id) {
        Genre genre;
        String sql = "SELECT * FROM genres WHERE id = :id";
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("id", id);
        try {
            genre = jdbc.queryForObject(sql, source, new GnreRowMapper());
            Objects.requireNonNull(genre);
        } catch (EmptyResultDataAccessException | NullPointerException ex) {
            return Optional.empty();
        }
        return Optional.of(genre);
    }

    /**
     * Маппер
     */
    private static class GnreRowMapper implements RowMapper<Genre> {

        @Override
        public Genre mapRow(ResultSet rs, int i) throws SQLException {
            Genre genre = new Genre();
            genre.setId(rs.getLong("id"));
            genre.setName(rs.getString("name"));
            return genre;
        }

    }

}