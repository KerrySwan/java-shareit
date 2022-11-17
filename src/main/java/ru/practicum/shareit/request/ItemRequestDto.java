package ru.practicum.shareit.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
public class ItemRequestDto {

    private long id;
    private String description;
    private User requester;
    private LocalDateTime created;

    @JsonCreator
    public ItemRequestDto(long id, String description, LocalDateTime created) {
        this.id = id;
        this.description = description;
        this.created = created;
    }

}
