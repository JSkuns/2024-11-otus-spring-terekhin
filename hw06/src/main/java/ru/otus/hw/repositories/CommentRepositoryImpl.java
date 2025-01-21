package ru.otus.hw.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.models.Comment;

import java.util.List;
import java.util.Optional;

@Repository
public class CommentRepositoryImpl implements CommentRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Comment> findById(long id) {
        return Optional.ofNullable(entityManager.find(Comment.class, id));
    }

    @Override
    public List<Comment> findAllCommentsByBookId(long bookId) {
        return entityManager
                .createQuery("SELECT c FROM comment c WHERE c.book.id = :book_id", Comment.class)
                .setParameter("book_id", bookId)
                .getResultList();
    }

    @Transactional
    @Override
    public Comment save(Comment comment) {
        return entityManager.merge(comment);
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        entityManager
                .createQuery("DELETE FROM comment c WHERE c.id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }

}