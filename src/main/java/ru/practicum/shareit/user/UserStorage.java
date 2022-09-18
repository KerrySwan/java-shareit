package ru.practicum.shareit.user;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.common.IStorage;
import ru.practicum.shareit.common.excepton.AlreadyExistsException;
import ru.practicum.shareit.common.excepton.DoesNotExistsException;

import javax.validation.constraints.NotNull;
import java.util.*;

@Component
public class UserStorage implements IStorage<User> {

    private Map<Long, User> users = new HashMap<>();

    private long currentId;

    public long getNextId() {
        return ++currentId;
    }

    @Override
    public User add(@NotNull User user) {
        throwIfEmailExist(user);
        user.setId(getNextId());
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User update(@NotNull User user) {
        long id = user.getId();
        throwIfUserNotExist(id);
        throwIfEmailExist(user);
        User u = get(id).update(user);
        users.put(u.getId(), u);
        return u;
    }

    @Override
    public void remove(long id) {
        users.remove(id);
    }

    @Override
    public User get(long id) {
        throwIfUserNotExist(id);
        return users.get(id);
    }

    @Override
    public List<User> get() {
        return new ArrayList<>(users.values());
    }


    private void throwIfEmailExist(User user) throws AlreadyExistsException {
        for (User u : users.values()) {
            if (u.getEmail().equals(user.getEmail())) throw new AlreadyExistsException("Email is already taken");
        }
    }

    private void throwIfUserExist(long id) throws AlreadyExistsException{
        if (users.containsKey(id)) throw new AlreadyExistsException("User already exists");
    }

    private void throwIfUserExist(User user) throws AlreadyExistsException{
        throwIfUserExist(user.getId());
    }

    private void throwIfUserNotExist(long id) throws DoesNotExistsException {
        if (!users.containsKey(id)) throw new DoesNotExistsException("User does not exists");
    }

    private void throwIfUserNotExist(User user) throws DoesNotExistsException {
        throwIfUserNotExist(user.getId());
    }
}
