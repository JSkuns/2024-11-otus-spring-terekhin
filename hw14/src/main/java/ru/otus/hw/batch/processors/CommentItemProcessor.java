package ru.otus.hw.batch.processors;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import ru.otus.hw.models.h2.Book;
import ru.otus.hw.models.h2.Comment;
import ru.otus.hw.models.mongo.MongoBook;
import ru.otus.hw.models.mongo.MongoComment;

@Component
public class CommentItemProcessor implements ItemProcessor<Comment, MongoComment> {

    @Override
    public MongoComment process(Comment comment) {
        MongoComment mongoComment = new MongoComment();
        mongoComment.setId(String.valueOf(comment.getId()));
        mongoComment.setText(comment.getText());
        mongoComment.setMongoBook(mapBook(comment.getBook()));
        return mongoComment;
    }

    private MongoBook mapBook(Book book) {
        return new BookItemProcessor().process(book);
    }

}
