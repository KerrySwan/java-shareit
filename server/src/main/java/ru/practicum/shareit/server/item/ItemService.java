package ru.practicum.shareit.server.item;

import java.util.List;

public interface ItemService {

    ItemDto addItem(long userId, ItemDto itemDto);

    CommentDto addComment(long userId, long itemId, CommentDto commentDto);

    ItemDto updateItem(long userId, ItemDto itemDto);

    ItemDto getItem(long userid, long itemId);

    List<ItemDto> getAll(long userId, int from, int size);

    List<ItemDto> find(String pattern, int from, int size);

}
