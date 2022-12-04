package ru.practicum.shareit.gateway.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.gateway.user.User;

import java.time.LocalDateTime;

/**
 * TODO Sprint add-item-requests.
 */
@AllArgsConstructor
@Getter
@Setter
public class ItemRequestDto {

    private final long id;
    private final String description;
    private final User requestor;
    private final LocalDateTime created;

}
