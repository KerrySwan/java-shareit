package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final IUserStorage<User> userStorage;

    @Override
    public UserDto addUser(UserDto userDto) {
        User user = userStorage.add(UserMapper.toModel(userDto));
        return UserMapper.toDto(user);
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        User user = userStorage.update(UserMapper.toModel(userDto));
        return UserMapper.toDto(user);
    }

    @Override
    public void deleteUser(long id) {
        userStorage.remove(id);
    }

    @Override
    public UserDto getUser(long id) {
        return UserMapper.toDto(userStorage.get(id));
    }

    @Override
    public List<UserDto> getUsers() {
        return userStorage.get().stream().map(UserMapper::toDto).collect(Collectors.toList());
    }
}
