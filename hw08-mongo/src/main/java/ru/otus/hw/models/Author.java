package ru.otus.hw.models;

import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Builder
@Document(collection = "authors")
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Author {

    @Id
    private String id;

    private String fullName;

}