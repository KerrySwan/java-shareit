package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingDtoIdOnly;
import ru.practicum.shareit.booking.BookingMapper;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.common.exception.DoesNotExistsException;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemStorage;
    private final UserRepository userStorage;
    private final BookingRepository bookingStorage;

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
    public ItemDto getItem(long userId, long itemId) {
        Item item = itemStorage.findById(itemId).orElseThrow();
        item = setLastAndNextBooking(item, userId);
        return ItemMapper.toDto(item);
    }

    @Override
    public List<ItemDto> getAll(long userId) {
        List<Item> items = itemStorage.findAll(Sort.by(Sort.Direction.ASC, "id"));
        return items.stream()
                .filter(item -> item.getOwnerId() == userId)
                .map(i -> setLastAndNextBooking(i, userId))
                .map(ItemMapper::toDto)
                .collect(Collectors.toList());
    }

    private Item setLastAndNextBooking(Item item, long bookerId){
        Comparator<Booking> c = new Comparator<>() {
            @Override
            public int compare(Booking o1, Booking o2) {
                return o1.getStart().isBefore(o2.getStart()) ? -1 : o1.getStart().isAfter(o2.getStart()) ? 1 : 0;
            }
        };
        List<Booking> bookings = bookingStorage.findAllByItemId(item.getId(), bookerId);
        bookings.sort(c);
        for (Booking booking : bookings) {
            item.setNextBooking(BookingMapper.toDtoIdOnly(booking));
        }
        return item;
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
