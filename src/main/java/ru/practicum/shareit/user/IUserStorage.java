package ru.practicum.shareit.user;

import java.util.List;

public interface IUserStorage<T> {

    T add(T t);

    T update(T t);

    void remove(long id);

    T get(long id);

    List<T> get();

}
