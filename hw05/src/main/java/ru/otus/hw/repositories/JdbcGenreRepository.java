package ru.otus.hw.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.otus.hw.models.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class JdbcGenreRepository implements GenreRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Используется в {@link ru.otus.hw.services.GenreServiceImpl}
     * А тот в свою очередь в {@link ru.otus.hw.commands.GenreCommands}
     * Должны получить все жанры из БД
     */
    @Override
    public List<Genre> findAll() {

        String sql = "SELECT * FROM genres";

        return jdbcTemplate.query(
                sql,
                new GnreRowMapper());
    }

    /**
     * Используется в {@link ru.otus.hw.services.BookServiceImpl} при сохранении книги в БД
     */
    @Override
    public Optional<Genre> findById(long id) {

        String sql = "SELECT * FROM genres WHERE id = ?";

        Genre genre = jdbcTemplate.queryForObject(sql, new GnreRowMapper(), id);

        return Optional.of(Objects.requireNonNull(genre)); // NPE обрабатывается в BookServiceImpl
    }

    /**
     * Mapper
     */
    private static class GnreRowMapper implements RowMapper<Genre> {

        @Override
        public Genre mapRow(ResultSet rs, int i) throws SQLException {
            // Создаём жанр
            Genre genre = new Genre();

            // Заполняем поля
            genre.setId(rs.getLong("ID"));
            genre.setName(rs.getString("NAME"));

            // Вернём жанр
            return genre;
        }

    }

}