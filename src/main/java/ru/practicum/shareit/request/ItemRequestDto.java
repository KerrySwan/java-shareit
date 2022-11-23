package ru.practicum.shareit.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.item.ItemDto;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserDto;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
public class ItemRequestDto {

    private long id;
    private String description;
    private UserDto requester;
    private LocalDateTime created;
    private List<ItemDto> items;

    @JsonCreator
    public ItemRequestDto(long id, String description, LocalDateTime created) {
        this.id = id;
        this.description = description;
        this.created = created;
    }

    public ItemRequestDto(long id, String description, UserDto requester, LocalDateTime created) {
        this.id = id;
        this.description = description;
        this.requester = requester;
        this.created = created;
    }

    public ItemRequestDto(long id, String description, UserDto requester, LocalDateTime created, List<ItemDto> items) {
        this.id = id;
        this.description = description;
        this.requester = requester;
        this.created = created;
        this.items = items == null ? Collections.emptyList() : items;
    }
}
