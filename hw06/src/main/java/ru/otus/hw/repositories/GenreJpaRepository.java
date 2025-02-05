package ru.otus.hw.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.hw.models.Genre;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class GenreJpaRepository implements GenreRepository {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Получить все жанры из БД
     */
    @Override
    public List<Genre> findAll() {
        return entityManager
                .createQuery("SELECT g FROM Genre g", Genre.class)
                .getResultList();
    }

    /**
     * Найти жанр по ID
     */
    @Override
    public Optional<Genre> findById(long id) {
        return Optional.ofNullable(entityManager.find(Genre.class, id));
    }

}