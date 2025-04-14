package ru.otus.hw.batch.processors;

import lombok.AllArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import ru.otus.hw.models.h2.Comment;
import ru.otus.hw.models.mongo.MongoComment;
import ru.otus.hw.repositories.mongo.MongoBookRepository;

@AllArgsConstructor
@Component
public class CommentItemProcessor implements ItemProcessor<Comment, MongoComment> {

    private final MongoBookRepository mongoBookRepository;

    @Override
    public MongoComment process(Comment comment) {
        MongoComment mongoComment = new MongoComment();
        mongoComment.setId(String.valueOf(comment.getId()));
        mongoComment.setText(comment.getText());
        var mongoBook = mongoBookRepository
                .findById(String.valueOf(comment.getBook().getId()))
                .orElse(null);
        mongoComment.setMongoBook(mongoBook);
        return mongoComment;
    }

}
