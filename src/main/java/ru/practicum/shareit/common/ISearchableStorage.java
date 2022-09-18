package ru.practicum.shareit.common;

import java.util.List;

public interface ISearchableStorage<T extends Model> extends IStorage<T> {

    List<T> find(String pattern);
}
