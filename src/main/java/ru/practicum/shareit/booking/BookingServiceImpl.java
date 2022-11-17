package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.common.exception.AccessException;
import ru.practicum.shareit.common.exception.InvalidUserException;
import ru.practicum.shareit.common.exception.ItemIsAnavailableException;
import ru.practicum.shareit.common.exception.UnsupportedStatusException;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    Sort descSortByStart = Sort.by(Sort.Direction.DESC, "start");

    @Override
    @Transactional
    public BookingDto add(long userId, BookingDtoIdOnly bookingDtoIdOnly) {
        BookingDto b = initBooker(userId, bookingDtoIdOnly);
        b.setStatus(BookingStatus.WAITING);
        if (!b.getItem().getAvailable())
            throw new ItemIsAnavailableException(String.format("item id = %s is anavailable", bookingDtoIdOnly.getItemId()));
        if (b.getItem().getOwnerId() == userId)
            throw new InvalidUserException("User can't book his own item");
        Booking booking = BookingMapper.toModel(b);
        booking = bookingRepository.save(booking);
        return BookingMapper.toDto(booking);
    }

    @Override
    @Transactional
    public BookingDto changeStatus(long userId, long bookingId, boolean isApproved) {
        BookingStatus bs = isApproved ? BookingStatus.APPROVED : BookingStatus.REJECTED;
        Booking b = bookingRepository.findById(bookingId).orElseThrow();
        if (b.getStatus() == BookingStatus.APPROVED)
            throw new AccessException("Status of booking can't be changed after approval");
        if (b.getBooker().getId() == userId) throw new InvalidUserException("Booker can't change status of an booking");
        b.setStatus(bs);
        bookingRepository.save(b);
        return BookingMapper.toDto(b);
    }

    @Override
    public BookingDto getByUserIdAndBookingId(long userId, long bookingId) {
        userRepository.findById(userId).orElseThrow();
        Booking booking = bookingRepository.findById(bookingId).orElseThrow();
        if (!(booking.getBooker().getId() == userId || booking.getItem().getOwnerId() == userId))
            throw new InvalidUserException(String.format("User id = %d and booking id = %d are not linked", userId, bookingId));
        return BookingMapper.toDto(booking);
    }

    @Override
    public List<BookingDto> findAllByUserId(long userId) {
        userRepository.findById(userId).orElseThrow();
        List<Booking> bookingList = bookingRepository.findAllByBookerId(userId, descSortByStart);
        return bookingList.stream()
                .map(BookingMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookingDto> findAllByOwnerId(long ownerId) {
        userRepository.findById(ownerId).orElseThrow();
        List<Booking> bookingList = bookingRepository.findAllByItemOwnerId(ownerId, descSortByStart);
        return bookingList.stream()
                .map(BookingMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookingDto> getUserIdAndByState(long userId, String state) {
        userRepository.findById(userId).orElseThrow();
        List<Booking> result = Collections.emptyList();
        try {
            switch (BookingState.valueOf(state)) {
                case ALL:
                    result = bookingRepository.findAllByBookerId(userId, descSortByStart);
                    break;
                case PAST:
                    result = bookingRepository.findAllByBookerIdAndEndIsBefore(userId, LocalDateTime.now(), descSortByStart);
                    break;
                case CURRENT:
                    result = bookingRepository.findAllByBookerIdAndStartIsBeforeAndEndIsAfter(userId, LocalDateTime.now(), descSortByStart);
                    break;
                case FUTURE:
                    result = bookingRepository.findAllByBookerIdAndStartIsAfter(userId, LocalDateTime.now(), descSortByStart);
                    break;
                case WAITING:
                    result = bookingRepository.findAllByBookerIdAndStatus(userId, BookingStatus.WAITING);
                    break;
                case REJECTED:
                    result = bookingRepository.findAllByBookerIdAndStatus(userId, BookingStatus.REJECTED);
                    break;
            }
        } catch (IllegalArgumentException e) {
            throw new UnsupportedStatusException("Unknown state: " + state);
        }
        return result.stream()
                .map(BookingMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookingDto> getOwnerIdAndByState(long ownerId, String state) {
        userRepository.findById(ownerId).orElseThrow();
        List<Booking> result = Collections.emptyList();
        try {
            switch (BookingState.valueOf(state)) {
                case ALL:
                    result = bookingRepository.findAllByItemOwnerId(ownerId, descSortByStart);
                    break;
                case PAST:
                    result = bookingRepository.findAllByOwnerIdAndEndIsBefore(ownerId, LocalDateTime.now(), descSortByStart);
                    break;
                case CURRENT:
                    result = bookingRepository.findAllByOwnerIdAndStartIsBeforeAndEndIsAfter(ownerId, LocalDateTime.now(), descSortByStart);
                    break;
                case FUTURE:
                    result = bookingRepository.findAllByOwnerIdAndStartIsAfter(ownerId, LocalDateTime.now(), descSortByStart);
                    break;
                case WAITING:
                    result = bookingRepository.findAllByOwnerIdAndStatus(ownerId, BookingStatus.WAITING);
                    break;
                case REJECTED:
                    result = bookingRepository.findAllByOwnerIdAndStatus(ownerId, BookingStatus.REJECTED);
                    break;
            }
        } catch (IllegalArgumentException e) {
            throw new UnsupportedStatusException("Unknown state: " + state);
        }
        return result.stream()
                .map(BookingMapper::toDto)
                .collect(Collectors.toList());
    }

    private BookingDto initBooker(long userId, BookingDtoIdOnly bookingDtoIdOnly) {
        User u = userRepository.findById(userId).orElseThrow();
        Item i = itemRepository.findById(bookingDtoIdOnly.getItemId()).orElseThrow();
        BookingDto b = BookingMapper.toFullDto(bookingDtoIdOnly);
        b.setBooker(u);
        b.setItem(i);
        return b;
    }

}
