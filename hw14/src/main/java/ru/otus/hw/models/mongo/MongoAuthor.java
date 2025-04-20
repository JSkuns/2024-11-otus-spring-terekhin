package ru.otus.hw.models.mongo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Builder
@Document(collection = "authors")
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class MongoAuthor {

    @Id
    private String id;

    private String fullName;

}