package ru.otus.hw.models.mongo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Builder
@Document(collection = "books")
@AllArgsConstructor
@NoArgsConstructor
public class MongoBook {

    @Id
    private String id;

    private String title;

    private MongoAuthor mongoAuthor;

    private MongoGenre mongoGenre;

}