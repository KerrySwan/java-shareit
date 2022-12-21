package ru.practicum.shareit.server.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userStorage;

    @Override
    @Transactional
    public UserDto addUser(UserDto userDto) {
        User user = userStorage.save(UserMapper.toModel(userDto));
        return UserMapper.toDto(user);
    }

    @Override
    @Transactional
    public UserDto updateUser(UserDto userDto) {
        User oldData = userStorage.findById(userDto.getId()).orElseThrow();
        User user = updateUserEntity(oldData, UserMapper.toModel(userDto));
        user = userStorage.save(user);
        return UserMapper.toDto(user);
    }

    @Override
    @Transactional
    public void deleteUser(long id) {
        userStorage.deleteById(id);
    }

    @Override
    public UserDto getUser(long id) {
        return UserMapper.toDto(userStorage.getById(id));
    }

    @Override
    public List<UserDto> getUsers() {
        return userStorage.findAll().stream().map(UserMapper::toDto).collect(Collectors.toList());
    }

    private User updateUserEntity(User user, User updUserData) {
        if (updUserData.getName() != null) user.setName(updUserData.getName());
        if (updUserData.getEmail() != null) user.setEmail(updUserData.getEmail());
        return user;
    }
}
