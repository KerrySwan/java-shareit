package ru.practicum.shareit.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.common.IMapper;
import ru.practicum.shareit.common.ISearchableStorage;
import ru.practicum.shareit.common.IStorage;
import ru.practicum.shareit.common.excepton.DoesNotExistsException;
import ru.practicum.shareit.user.User;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemService {

    private final IMapper<Item, ItemDto> itemMapper;
    private final ISearchableStorage<Item> itemStorage;
    private final IStorage<User> userStorage;


    @Autowired
    public ItemService(@Qualifier("itemMapper") IMapper<Item, ItemDto> itemMapper,
                       @Qualifier("itemStorage") ISearchableStorage<Item> itemStorage,
                       @Qualifier("userStorage") IStorage<User> userStorage) {
        this.itemMapper = itemMapper;
        this.itemStorage = itemStorage;
        this.userStorage = userStorage;
    }

    public ItemDto addItem(long userId, ItemDto itemDto) {
        User user = userStorage.get(userId);
        itemDto.setOwner(user);
        Item item = itemStorage.add(itemMapper.toModel(itemDto));
        return itemMapper.toDto(item);
    }

    public ItemDto updateItem(long userId, ItemDto itemDto) {
        User user = getUserIfBelongs(userId, itemDto.getId());
        itemDto.setOwner(user);
        Item item = itemStorage.update(itemMapper.toModel(itemDto));
        return itemMapper.toDto(item);
    }

    public ItemDto getItem(long userId, long itemId) {
        getUserIfBelongs(userId, itemId);
        Item item = itemStorage.get(itemId);
        return itemMapper.toDto(item);
    }

    public List<ItemDto> getAll(long userId) {
        List<Item> items = itemStorage.get();
        return items.stream()
                .filter(item -> item.getOwner().getId() == userId)
                .map(itemMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<ItemDto> find(String pattern){
        List<Item> items = itemStorage.find(pattern);
        return items.stream()
                .map(itemMapper::toDto)
                .collect(Collectors.toList());
    }

    private User getUserIfBelongs(long userId, long itemId) {
        User user = userStorage.get(userId);
        Item item = itemStorage.get(itemId);
        if (!item.getOwner().equals(user)) {
            String msg = String.format("Such an item with id=%s does not belong to user id=%s.", itemId, userId);
            throw new DoesNotExistsException(msg);
        }
        return user;
    }
}
