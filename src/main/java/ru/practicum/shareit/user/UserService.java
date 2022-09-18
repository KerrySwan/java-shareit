package ru.practicum.shareit.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.common.IMapper;
import ru.practicum.shareit.common.IStorage;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final IMapper<User, UserDto> userMapper;
    private final IStorage<User> userStorage;

    @Autowired
    public UserService(@Qualifier("userMapper") IMapper<User, UserDto> userMapper, @Qualifier("userStorage") IStorage<User> userStorage) {
        this.userMapper = userMapper;
        this.userStorage = userStorage;
    }

    public UserDto addUser(UserDto userDto) {
        User user = userStorage.add(userMapper.toModel(userDto));
        return userMapper.toDto(user);
    }

    public UserDto updateUser(UserDto userDto) {
        User user = userStorage.update(userMapper.toModel(userDto));
        return userMapper.toDto(user);
    }

    public void deleteUser(long id) {
        userStorage.remove(id);
    }

    public UserDto getUser(long id) {
        return userMapper.toDto(userStorage.get(id));
    }

    public List<UserDto> getUsers() {
        return userStorage.get().stream().map(userMapper::toDto).collect(Collectors.toList());
    }
}
