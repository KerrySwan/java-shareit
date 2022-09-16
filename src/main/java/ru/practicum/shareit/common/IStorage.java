package ru.practicum.shareit.common;

import java.util.List;

public interface IStorage<T extends Model> {

    T add(T t);

    T update(T t);

    void remove(long id);

    T get(long id);

    List<T> get();

}
