package ru.practicum.shareit.server.item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CommentDto {

    private long id;
    private String text;
    private long itemId;
    private String authorName;

}
