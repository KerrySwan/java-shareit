package ru.practicum.shareit.gateway.item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

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
