package ru.otus.hw.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.otus.hw.models.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class JdbcAuthorRepository implements AuthorRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Используется в {@link ru.otus.hw.services.AuthorServiceImpl}
     * А тот в свою очередь используется в {@link ru.otus.hw.commands.AuthorCommands}
     * Должны получить всех авторов из БД
     */
    @Override
    public List<Author> findAll() {

        String sql = "SELECT * FROM authors";

        return jdbcTemplate.query(
                sql,
                new AuthorRowMapper());
    }

    /**
     * Используется в {@link ru.otus.hw.services.BookServiceImpl} при сохранении книги в БД
     */
    @Override
    public Optional<Author> findById(long id) {

        String sql = "SELECT * FROM authors WHERE id = ?";

        Author author = jdbcTemplate.queryForObject(sql, new AuthorRowMapper(), id);

        return Optional.of(Objects.requireNonNull(author)); // NPE обрабатывается в BookServiceImpl
    }

    /**
     * Mapper
     */
    private static class AuthorRowMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet rs, int i) throws SQLException {
            // Создаём автора
            Author author = new Author();

            // Заполняем поля
            author.setId(rs.getLong("ID"));
            author.setFullName(rs.getString("FULL_NAME"));

            // Вернём автора
            return author;
        }

    }

}