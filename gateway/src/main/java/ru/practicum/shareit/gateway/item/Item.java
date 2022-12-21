package ru.practicum.shareit.gateway.item;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Item {


    private long id;
    private String name;
    private String description;
    private Boolean available;
    private long ownerId;
    private long requestId;

    public Item() {
    }

}
