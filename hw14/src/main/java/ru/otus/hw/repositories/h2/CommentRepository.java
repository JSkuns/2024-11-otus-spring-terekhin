package ru.otus.hw.repositories.h2;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.hw.models.h2.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByBookId(Long bookId);

}
