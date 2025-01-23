package ru.otus.hw.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.models.Genre;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class JpaGenreRepository implements GenreRepository {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Получить все жанры из БД
     */
    @Override
    @Transactional(readOnly = true)
    public List<Genre> findAll() {
        return entityManager
                .createQuery("SELECT g FROM Genre g", Genre.class)
                .getResultList();
    }

    /**
     * Найти жанр по ID
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Genre> findById(long id) {
        return entityManager
                .createQuery("SELECT g FROM Genre g WHERE g.id = :id", Genre.class)
                .setParameter("id", id)
                .getResultList()
                .stream()
                .findFirst();
    }

}