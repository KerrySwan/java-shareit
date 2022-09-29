package ru.practicum.shareit.item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.user.User;

/**
 * TODO Sprint add-controllers.
 */
@AllArgsConstructor
@Getter
@Setter
public class Item {

    private long id;
    private String name;
    private String description;
    private Boolean available;
    private User owner;
    private ItemRequest request;

    public Item() {
    }

}
