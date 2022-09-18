package ru.practicum.shareit.item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.common.Model;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.user.User;

/**
 * TODO Sprint add-controllers.
 */
@AllArgsConstructor
@Getter
@Setter
public class Item implements Model {

    private long id;
    private String name;
    private String description;
    private Boolean available;
    private User owner;
    private ItemRequest request;

    public Item() {
    }

    public Item update(Item item){
        if(item.getName() != null) this.setName(item.getName());
        if(item.getDescription() != null) this.setDescription(item.getDescription());
        if(item.getAvailable() != null) this.setAvailable(item.getAvailable());
        if(item.getOwner() != null) this.setOwner(item.getOwner());
        if(item.getRequest() != null) this.setRequest(item.getRequest());
        return this;
    }

}
