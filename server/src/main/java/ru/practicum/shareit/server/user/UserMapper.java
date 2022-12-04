package ru.practicum.shareit.server.user;

import org.springframework.stereotype.Component;

@Component
public class UserMapper {


    public static UserDto toDto(User user) {
        return new UserDto(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }


    public static User toModel(UserDto dto) {
        return new User(
                dto.getId(),
                dto.getName(),
                dto.getEmail()
        );
    }
}
