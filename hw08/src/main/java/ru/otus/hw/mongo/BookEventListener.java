package ru.otus.hw.mongo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.stereotype.Component;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Book;
import ru.otus.hw.repositories.CommentRepository;

import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class BookEventListener extends AbstractMongoEventListener<Book> {

    private final CommentRepository commentRepository;

    @Override
    public void onBeforeDelete(BeforeDeleteEvent<Book> event) {
        try {
            String id = String.valueOf(Objects.requireNonNull(event.getDocument()).get("_id"));
            commentRepository.deleteByBookId(id);
            log.info("Comments for the book with id %s have been deleted".formatted(id));
        } catch (NullPointerException ignored) {
            var errMsg = "The comments for the book have not been deleted";
            log.error(errMsg);
            throw new EntityNotFoundException(errMsg);
        }
    }

}