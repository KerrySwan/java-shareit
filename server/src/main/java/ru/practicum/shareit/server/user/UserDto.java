package ru.practicum.shareit.server.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UserDto {

    private long id;
    private final String name;
    private final String email;

}
