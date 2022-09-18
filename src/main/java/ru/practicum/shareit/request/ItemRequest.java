package ru.practicum.shareit.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.common.Model;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;

/**
 * TODO Sprint add-item-requests.
 */
@AllArgsConstructor
@Getter
@Setter
public class ItemRequest implements Model {

    private final long id;
    private final String description;
    private final User requestor;
    private final LocalDateTime created;

}
