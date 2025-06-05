package ru.otus.hw.repositories;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.hw.models.Comment;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class CommentJpaRepository implements CommentRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Comment> findById(long id) {
        EntityGraph<?> entityGraph = entityManager.getEntityGraph("comment-entity-graph");
        return entityManager
                .createQuery("SELECT c FROM Comment c WHERE c.id = :id", Comment.class)
                .setParameter("id", id)
                .setHint("javax.persistence.fetchgraph", entityGraph)
                .getResultList()
                .stream()
                .findFirst();
    }

    @Override
    public List<Comment> findAllCommentsByBookId(long bookId) {
        EntityGraph<?> entityGraph = entityManager.getEntityGraph("comment-entity-graph");
        return entityManager
                .createQuery("SELECT c FROM Comment c WHERE c.book.id = :book_id", Comment.class)
                .setParameter("book_id", bookId)
                .setHint("javax.persistence.fetchgraph", entityGraph)
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
    public void delete(Comment comment) {
        entityManager.remove(comment);
    }

    private Comment insert(Comment comment) {
        entityManager.persist(comment);
        entityManager.flush();
        return comment;
    }

    private Comment update(Comment comment) {
        return entityManager.merge(comment);
    }

}