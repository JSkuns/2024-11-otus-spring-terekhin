package ru.otus.hw.repositories;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.otus.hw.models.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
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
        String sql = "SELECT * FROM genres WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", id);
        return jdbc.query(sql, params, new GnreRowMapper()).stream().findFirst();
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