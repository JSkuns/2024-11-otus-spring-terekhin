package ru.otus.hw.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Comment;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class JpaCommentRepository implements CommentRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Comment> findById(long id) {
        try {
            return Optional.of(entityManager.find(Comment.class, id));
        } catch (NoResultException | NullPointerException ex) {
            return Optional.empty();
        }
    }

    @Override
    public List<Comment> findAllCommentsByBookId(long bookId) {
        return entityManager
                .createQuery("SELECT c FROM Comment c WHERE c.book.id = :book_id", Comment.class)
                .setParameter("book_id", bookId)
                .getResultList();
    }

    @Override
    public Comment save(Comment comment) {
        if (comment.getId() == 0) {
            return insert(comment);
        }
        return update(comment);
    }

    @Override
    public void deleteById(long id) {
        entityManager
                .createQuery("DELETE FROM Comment c WHERE c.id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }

    private Comment insert(Comment comment) {
        entityManager.persist(comment);
        entityManager.flush();
        return comment;
    }

    private Comment update(Comment comment) {
        if (findById(comment.getId()).isPresent()) {
            return entityManager.merge(comment);
        } else {
            throw new EntityNotFoundException("Comment with id %d not found".formatted(comment.getId()));
        }
    }

}