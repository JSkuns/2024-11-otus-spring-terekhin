package ru.otus.hw.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.models.Book;

import java.util.List;
import java.util.Optional;

@Repository
public class BookRepositoryImpl implements BookRepository {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Найти книгу по ID
     */
    @Override
    public Optional<Book> findById(long id) {
        return Optional.ofNullable(entityManager.find(Book.class, id));
        /*
        Другой вариант:
        return entityManager
                .createQuery("SELECT b FROM book b WHERE b.id = :id", Book.class)
                .setParameter("id", id)
                .getResultList()
                .stream()
                .findFirst();
         */
    }

    /**
     * Получить все книги из БД
     */
    @Override
    public List<Book> findAll() {
        return entityManager
                .createQuery("SELECT b FROM book b JOIN author JOIN genre", Book.class)
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
                .createQuery("DELETE FROM book b WHERE b.id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }

    /**
     * Добавление новой книги в БД
     */
    private Book insert(Book book) {
        /*
        Другой вариант:
        entityManager
            .createNativeQuery("INSERT INTO books (title, author_id, genre_id) " +
                "VALUES (:title, :author_id, :genre_id)", Book.class)
            .setParameter("title", book.getTitle())
            .setParameter("author_id", book.getAuthor().getId())
            .setParameter("genre_id", book.getGenre().getId())
            .executeUpdate();
         Но тогда нужно будет отдельно получать сгенерированное ID.
         */
        return entityManager.merge(book);
    }

    /**
     * Меняем запись, или вставляем новую
     */
    private Book update(Book book) {
        // Можно объединить с insert, так как сейчас одинаковый вызов.
        return entityManager.merge(book);
    }

}