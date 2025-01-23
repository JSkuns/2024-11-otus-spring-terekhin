package ru.otus.hw.repositories;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class JpaBookRepository implements BookRepository {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Найти книгу по ID
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Book> findById(long id) {
        try {
            return Optional.of(entityManager.find(Book.class, id));
        } catch (NoResultException | NullPointerException ex) {
            return Optional.empty();
        }
    }

    /**
     * Получить все книги из БД
     */
    @Override
    @Transactional(readOnly = true)
    public List<Book> findAll() {
        return entityManager
                .createQuery("SELECT b FROM Book b", Book.class)
                .getResultList();
    }

    /**
     * Сохраняем или изменяем книгу (В зависимости от ID объекта Book).
     */
    @Override
    @Transactional
    public Book save(Book book) {
        if (book.getId() == 0) {
            return insert(book);
        }
        return update(book);
    }

    /**
     * Удалить какую-либо книгу по Id.
     */
    @Override
    @Transactional
    public void deleteById(long id) {
        entityManager
                .createQuery("DELETE FROM Book b WHERE b.id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }

    /**
     * Добавление новой книги в БД
     */
    private Book insert(Book book) {
        entityManager.persist(book);
        entityManager.flush();
        return book;
    }

    /**
     * Меняем запись, или вставляем новую
     */
    private Book update(Book book) {
        if (findById(book.getId()).isPresent()) {
            return entityManager.merge(book);
        } else {
            throw new EntityNotFoundException("Book with id %d not found".formatted(book.getId()));
        }
    }

}