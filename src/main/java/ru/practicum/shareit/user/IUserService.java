package ru.practicum.shareit.user;

import java.util.List;

public interface IUserService {

    UserDto addUser(UserDto userDto);

    UserDto updateUser(UserDto userDto);

    void deleteUser(long id);

    UserDto getUser(long id);

    List<UserDto> getUsers();

}
