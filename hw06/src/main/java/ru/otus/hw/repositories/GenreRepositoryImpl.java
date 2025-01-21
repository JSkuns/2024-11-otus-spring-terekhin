package ru.otus.hw.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import ru.otus.hw.models.Genre;

import java.util.List;
import java.util.Optional;

@Repository
public class GenreRepositoryImpl implements GenreRepository {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Получить все жанры из БД
     */
    @Override
    public List<Genre> findAll() {
        return entityManager
                .createQuery("SELECT g FROM genre g", Genre.class)
                .getResultList();
    }

    /**
     * Найти жанр по ID
     */
    @Override
    public Optional<Genre> findById(long id) {
        return entityManager
                .createQuery("SELECT g FROM genre g WHERE g.id = :id", Genre.class)
                .setParameter("id", id)
                .getResultList()
                .stream()
                .findFirst();
    }

}