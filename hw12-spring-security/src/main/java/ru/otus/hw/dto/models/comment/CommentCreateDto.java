package ru.otus.hw.dto.models.comment;

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
public class CommentCreateDto {

    @Digits(integer = 3, fraction = 0)
    private long bookId;

    @Size(min = 2, max = 50)
    private String text;

}
