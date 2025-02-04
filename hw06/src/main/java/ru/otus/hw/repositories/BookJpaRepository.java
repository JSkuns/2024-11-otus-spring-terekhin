package ru.otus.hw.repositories;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.hw.models.Book;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class BookJpaRepository implements BookRepository {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Найти книгу по ID
     */
    @Override
    public Optional<Book> findById(long id) {
        EntityGraph<?> entityGraph = entityManager.getEntityGraph("book-entity-graph");
        return entityManager
                .createQuery("SELECT b FROM Book b WHERE b.id = :id", Book.class)
                .setParameter("id", id)
                .setHint("javax.persistence.fetchgraph", entityGraph)
                .getResultList()
                .stream()
                .findFirst();
    }

    /**
     * Получить все книги из БД
     */
    @Override
    public List<Book> findAll() {
        EntityGraph<?> entityGraph = entityManager.getEntityGraph("book-entity-graph");
        return entityManager
                .createQuery("SELECT b FROM Book b", Book.class)
                .setHint("javax.persistence.fetchgraph", entityGraph)
                .getResultList();
    }

    /**
     * Сохраняем или изменяем книгу (В зависимости от ID объекта Book).
     */
    @Override
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
    public void delete(Book book) {
        entityManager.remove(book);
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
        return entityManager.merge(book);
    }

}