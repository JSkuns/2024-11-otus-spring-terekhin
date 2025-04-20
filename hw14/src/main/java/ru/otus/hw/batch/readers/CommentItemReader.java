package ru.otus.hw.batch.readers;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;
import ru.otus.hw.models.h2.Comment;
import ru.otus.hw.repositories.h2.CommentRepository;

import java.util.Iterator;
import java.util.List;

@Component
@AllArgsConstructor
public class CommentItemReader implements ItemReader<Comment> {

    private static Iterator<Comment> iterator;

    private CommentRepository commentRepository;

    @PostConstruct
    public void initialize() {
        List<Comment> comments = commentRepository.findAll();
        iterator = comments.iterator();
    }

    @Override
    public Comment read() {
        if (!iterator.hasNext()) {
            return null;
        }
        return iterator.next();
    }

}
