package ru.otus.hw.dto.models.book;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookUpdateDto {

    @Digits(integer = 3, fraction = 0)
    private long id;

    @Size(min = 2, max = 50)
    private String title;

    @Digits(integer = 3, fraction = 0)
    private long authorId;

    @Digits(integer = 3, fraction = 0)
    private long genreId;

}
