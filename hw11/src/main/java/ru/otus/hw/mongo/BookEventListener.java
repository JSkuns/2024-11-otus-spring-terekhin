package ru.otus.hw.mongo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import ru.otus.hw.exceptions.NotFoundException;
import ru.otus.hw.models.Book;
import ru.otus.hw.repositories.CommentRepository;

import static java.lang.String.format;

@Slf4j
@Component
@RequiredArgsConstructor
public class BookEventListener extends AbstractMongoEventListener<Book> {

    private final CommentRepository commentRepository;

    @Override
    public void onBeforeDelete(BeforeDeleteEvent<Book> event) {
        Mono.justOrEmpty(event.getDocument().get("_id"))
                .cast(String.class)
                .flatMap(id -> commentRepository.deleteById(id)
                        .then(Mono.fromRunnable(() ->
                                log.info(format("Comments for the book with id %s have been deleted", id))))
                        .onErrorResume(error -> {
                            var errMsg = format("The comments for the book with id %s could not be deleted", id);
                            log.error(errMsg, error);
                            return Mono.error(new NotFoundException(errMsg));
                        }));
    }

}