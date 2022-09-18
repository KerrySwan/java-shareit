package ru.practicum.shareit.item;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.common.ISearchableStorage;
import ru.practicum.shareit.common.excepton.AlreadyExistsException;
import ru.practicum.shareit.common.excepton.DoesNotExistsException;

import java.util.*;

@Component
public class ItemStorage implements ISearchableStorage<Item> {

    Map<Long, Item> items = new HashMap<>();

    long currentId;

    public long getNextId() {
        return ++currentId;
    }

    @Override
    public Item add(Item item) {
        throwIfItemExist(item);
        item.setId(getNextId());
        items.put(item.getId(), item);
        return item;
    }

    @Override
    public Item update(Item item) {
        long id = item.getId();
        throwIfItemNotExist(id);
        Item i = get(id).update(item);
        items.put(i.getId(), i);
        return i;
    }

    @Override
    public void remove(long id) {
        items.remove(id);
    }

    @Override
    public Item get(long id) {
        throwIfItemNotExist(id);
        return items.get(id);
    }

    @Override
    public List<Item> get() {
        return new ArrayList<>(items.values());
    }

    @Override
    public List<Item> find(String pattern) {
        if (pattern.isBlank()) return Collections.emptyList();
        List<Item> res = new ArrayList<>();
        for (Item i : items.values()) {
            String name = i.getName().toLowerCase();
            String desc = i.getDescription().toLowerCase();
            boolean isContains = name.contains(pattern.toLowerCase()) || desc.contains(pattern.toLowerCase());
            boolean isAvailable = i.getAvailable();
            if (isContains && isAvailable) res.add(i);
        }
        return res;
    }

    private void throwIfItemExist(long id) throws AlreadyExistsException {
        if (items.containsKey(id)) throw new AlreadyExistsException("Item already exists");
    }

    private void throwIfItemExist(Item item) throws AlreadyExistsException {
        throwIfItemExist(item.getId());
    }

    private void throwIfItemNotExist(long id) throws DoesNotExistsException {
        if (!items.containsKey(id)) throw new DoesNotExistsException("Item does not exists");
    }

    private void throwIfItemNotExist(Item item) throws DoesNotExistsException {
        throwIfItemNotExist(item.getId());
    }
}
