package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.common.excepton.DoesNotExistsException;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemStorage;
    private final UserRepository userStorage;

    @Override
    @Transactional
    public ItemDto addItem(long userId, ItemDto itemDto) {
        User user = userStorage.findById(userId).orElseThrow();
        itemDto.setOwnerId(user.getId());
        Item item = itemStorage.save(ItemMapper.toModel(itemDto));
        return ItemMapper.toDto(item);
    }

    @Override
    @Transactional
    public ItemDto updateItem(long userId, ItemDto itemDto) {
        throwIfNotBelongs(userId, itemDto.getId());
        itemDto.setOwnerId(userId);
        Item oldData = itemStorage.findById(itemDto.getId()).orElseThrow();
        Item item = updateItemEntity(oldData, ItemMapper.toModel(itemDto));
        item = itemStorage.save(item);
        return ItemMapper.toDto(item);
    }

    @Override
    public ItemDto getItem(long itemId) {
        Item item = itemStorage.findById(itemId).orElseThrow();
        return ItemMapper.toDto(item);
    }

    @Override
    public List<ItemDto> getAll(long userId) {
        List<Item> items = itemStorage.findAll();
        return items.stream()
                .filter(item -> item.getOwnerId() == userId)
                .map(ItemMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ItemDto> find(String pattern) {
        List<Item> items = itemStorage.findAllByNameOrByDesc(pattern);
        return items.stream()
                .map(ItemMapper::toDto)
                .collect(Collectors.toList());
    }

    private void throwIfNotBelongs(long userId, long itemId) {
        User user = userStorage.findById(userId).orElseThrow();
        Item item = itemStorage.findById(itemId).orElseThrow();
        if (item.getOwnerId() != user.getId()) {
            String msg = String.format("Such an item with id=%s does not belong to user id=%s.", itemId, userId);
            throw new DoesNotExistsException(msg);
        }
    }

    public Item updateItemEntity(Item item, Item updItemData) {
        if (updItemData.getName() != null) item.setName(updItemData.getName());
        if (updItemData.getDescription() != null) item.setDescription(updItemData.getDescription());
        if (updItemData.getAvailable() != null) item.setAvailable(updItemData.getAvailable());
        if (updItemData.getOwnerId() > 0) item.setOwnerId(updItemData.getOwnerId());
        if (updItemData.getRequestId() > 0) item.setRequestId(updItemData.getRequestId());
        return item;
    }
}
