package ru.practicum.shareit.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final IUserStorage<User> userStorage;

    @Autowired
    public UserService(@Qualifier("userStorage") IUserStorage<User> userStorage) {
        this.userStorage = userStorage;
    }

    public UserDto addUser(UserDto userDto) {
        User user = userStorage.add(UserMapper.toModel(userDto));
        return UserMapper.toDto(user);
    }

    public UserDto updateUser(UserDto userDto) {
        User user = userStorage.update(UserMapper.toModel(userDto));
        return UserMapper.toDto(user);
    }

    public void deleteUser(long id) {
        userStorage.remove(id);
    }

    public UserDto getUser(long id) {
        return UserMapper.toDto(userStorage.get(id));
    }

    public List<UserDto> getUsers() {
        return userStorage.get().stream().map(UserMapper::toDto).collect(Collectors.toList());
    }
}
