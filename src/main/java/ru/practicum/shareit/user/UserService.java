package ru.practicum.shareit.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.common.IStorage;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final IStorage<User> userStorage;

    @Autowired
    public UserService(@Qualifier("userStorage") IStorage<User> userStorage) {
        this.userStorage = userStorage;
    }

    public UserDto addUser(UserDto userDto){
        User user = userStorage.add(UserMapper.mapToUser(userDto));
        return UserMapper.mapToDto(user);
    }

    public UserDto updateUser(UserDto userDto){
        User user = userStorage.update(UserMapper.mapToUser(userDto));
        return UserMapper.mapToDto(user);
    }

    public void deleteUser(long id){
        userStorage.remove(id);
    }

    public UserDto getUser(long id){
        return UserMapper.mapToDto(userStorage.get(id));
    }

    public List<UserDto> getUsers(){
        return userStorage.get().stream().map(UserMapper::mapToDto).collect(Collectors.toList());
    }
}
