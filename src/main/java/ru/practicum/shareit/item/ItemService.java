package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.common.excepton.DoesNotExistsException;
import ru.practicum.shareit.user.IUserStorage;
import ru.practicum.shareit.user.User;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemService implements IItemService {

    private final IItemStorage<Item> itemStorage;
    private final IUserStorage<User> userStorage;

    @Override
    public ItemDto addItem(long userId, ItemDto itemDto) {
        User user = userStorage.get(userId);
        itemDto.setOwnerId(user.getId());
        Item item = itemStorage.add(ItemMapper.toModel(itemDto));
        return ItemMapper.toDto(item);
    }

    @Override
    public ItemDto updateItem(long userId, ItemDto itemDto) {
        User user = getUserIfBelongs(userId, itemDto.getId());
        itemDto.setOwnerId(user.getId());
        Item item = itemStorage.update(ItemMapper.toModel(itemDto));
        return ItemMapper.toDto(item);
    }

    @Override
    public ItemDto getItem(long itemId) {
        Item item = itemStorage.get(itemId);
        return ItemMapper.toDto(item);
    }

    @Override
    public List<ItemDto> getAll(long userId) {
        List<Item> items = itemStorage.get();
        return items.stream()
                .filter(item -> item.getOwnerId() == userId)
                .map(ItemMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ItemDto> find(String pattern) {
        List<Item> items = itemStorage.find(pattern);
        return items.stream()
                .map(ItemMapper::toDto)
                .collect(Collectors.toList());
    }

    private User getUserIfBelongs(long userId, long itemId) {
        User user = userStorage.get(userId);
        Item item = itemStorage.get(itemId);
        if (item.getOwnerId() != user.getId()) {
            String msg = String.format("Such an item with id=%s does not belong to user id=%s.", itemId, userId);
            throw new DoesNotExistsException(msg);
        }
        return user;
    }
}
