package ru.otus.hw.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Builder
@Document(collection = "books")
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    @Id
    private String id;

    private String title;

    private Author author;

    private Genre genre;

}