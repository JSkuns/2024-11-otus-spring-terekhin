package ru.otus.hw.dto.models.book;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookCreateDto {

    @Size(min = 2, max = 50)
    private String title;

    @NotNull
    private Long authorId;

    @NotNull
    private Long genreId;

}
