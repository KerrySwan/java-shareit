package ru.practicum.shareit.item;

import java.util.List;

public interface IItemStorage<T> {

    T add(T t);

    T update(T t);

    void remove(long id);

    T get(long id);

    List<T> get();

    List<T> find(String pattern);

}
