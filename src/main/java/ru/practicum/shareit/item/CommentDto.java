package ru.practicum.shareit.item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@Getter
@Setter
public class CommentDto {

    private long id;
    @NotBlank
    private String text;
    private long itemId;
    private String authorName;

}
