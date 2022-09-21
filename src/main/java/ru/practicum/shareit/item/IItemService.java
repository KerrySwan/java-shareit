package ru.practicum.shareit.item;

import java.util.List;

public interface IItemService {

    ItemDto addItem(long userId, ItemDto itemDto);

    ItemDto updateItem(long userId, ItemDto itemDto);

    ItemDto getItem(long itemId);

    List<ItemDto> getAll(long userId);

    List<ItemDto> find(String pattern);

}
