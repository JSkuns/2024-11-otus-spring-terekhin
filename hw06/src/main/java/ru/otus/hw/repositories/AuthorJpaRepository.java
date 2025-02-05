package ru.otus.hw.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.hw.models.Author;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class AuthorJpaRepository implements AuthorRepository {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Получить всех авторов из БД
     */
    @Override
    public List<Author> findAll() {
        return entityManager
                .createQuery("SELECT a FROM Author a", Author.class)
                .getResultList();
    }

    /**
     * Найти автора по ID
     */
    @Override
    public Optional<Author> findById(long id) {
        return Optional.ofNullable(entityManager.find(Author.class, id));
    }

}