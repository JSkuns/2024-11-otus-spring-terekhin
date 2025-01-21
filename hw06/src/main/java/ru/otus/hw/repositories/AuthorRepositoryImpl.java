package ru.otus.hw.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import ru.otus.hw.models.Author;

import java.util.List;
import java.util.Optional;

@Repository
public class AuthorRepositoryImpl implements AuthorRepository {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Получить всех авторов из БД
     */
    @Override
    public List<Author> findAll() {
        return entityManager
                .createQuery("SELECT a FROM author a", Author.class)
                .getResultList();
    }

    /**
     * Найти автора по ID
     */
    @Override
    public Optional<Author> findById(long id) {
        return entityManager
                .createQuery("SELECT a FROM author a WHERE a.id = :id", Author.class)
                .setParameter("id", id)
                .getResultList()
                .stream()
                .findFirst();
    }

}