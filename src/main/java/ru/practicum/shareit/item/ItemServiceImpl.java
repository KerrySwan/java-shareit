package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingMapper;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.common.exception.DoesNotExistsException;
import ru.practicum.shareit.common.exception.ItemIsAnavailableException;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemStorage;
    private final UserRepository userStorage;
    private final BookingRepository bookingStorage;
    private final CommentRepository commentsStorage;

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
        ItemDto dto = ItemMapper.toDto(item);
        addCommentsToItem(dto);
        setLastAndNextBooking(dto, userId);
        return dto;
    }

    @Override
    public List<ItemDto> getAll(long userId) {
        List<Item> items = itemStorage.findAll(Sort.by(Sort.Direction.ASC, "id"));
        return items.stream()
                .filter(item -> item.getOwnerId() == userId)
                .map(ItemMapper::toDto)
                .map(i -> setLastAndNextBooking(i, userId))
                .map(this::addCommentsToItem)
                .collect(Collectors.toList());
    }

    private ItemDto addCommentsToItem(ItemDto itemDto) {
        List<Comment> comments = commentsStorage.findAllByItemId(itemDto.getId());
        itemDto.setComments(
                comments.stream()
                        .map(CommentMapper::toDto)
                        .collect(Collectors.toList())
        );
        return itemDto;
    }

    private ItemDto setLastAndNextBooking(ItemDto itemDto, long bookerId) {
        Comparator<Booking> c = new Comparator<>() {
            @Override
            public int compare(Booking o1, Booking o2) {
                return o1.getStart().isBefore(o2.getStart()) ? -1 : o1.getStart().isAfter(o2.getStart()) ? 1 : 0;
            }
        };
        List<Booking> bookings = bookingStorage.findAllByItemId(itemDto.getId(), bookerId, Pageable.unpaged());
        bookings.sort(c);
        for (Booking booking : bookings) {
            itemDto.setNextBooking(BookingMapper.toDtoIdOnly(booking));
        }
        return itemDto;
    }

    @Override
    public List<ItemDto> find(String pattern) {
        List<Item> items = itemStorage.findAllByNameOrByDesc(pattern);
        return items.stream()
                .map(ItemMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CommentDto addComment(long userId, long itemId, CommentDto commentDto) {
        User u = userStorage.findById(userId).orElseThrow();
        Item i = itemStorage.findById(itemId).orElseThrow();
        List<Booking> bookings = bookingStorage.findAllByItemIdAndBookerId(itemId, userId, LocalDateTime.now(), Pageable.unpaged());
        if (bookings.isEmpty())
            throw new ItemIsAnavailableException("Item have not been booked by this user yet.");
        Comment comment = CommentMapper.toModel(commentDto);
        comment.setAuthor(u);
        comment.setItem(i);
        return CommentMapper.toDto(
                commentsStorage.save(comment)
        );
    }

    private void throwIfNotBelongs(long userId, long itemId) {
        User user = userStorage.findById(userId).orElseThrow();
        Item item = itemStorage.findById(itemId).orElseThrow();
        if (item.getOwnerId() != user.getId()) {
            String msg = String.format("Such an item with id=%s does not belong to user id=%s.", itemId, userId);
            throw new DoesNotExistsException(msg);
        }
    }

    private Item updateItemEntity(Item item, Item updItemData) {
        if (updItemData.getName() != null) item.setName(updItemData.getName());
        if (updItemData.getDescription() != null) item.setDescription(updItemData.getDescription());
        if (updItemData.getAvailable() != null) item.setAvailable(updItemData.getAvailable());
        if (updItemData.getOwnerId() > 0) item.setOwnerId(updItemData.getOwnerId());
        if (updItemData.getRequestId() > 0) item.setRequestId(updItemData.getRequestId());
        return item;
    }
}
