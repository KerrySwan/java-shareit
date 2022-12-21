package ru.practicum.shareit.server.booking;


import java.util.List;

public interface BookingService {

    BookingDto add(long userId, BookingDtoIdOnly b);

    BookingDto changeStatus(long userId, long bookingId, boolean isApproved);

    BookingDto getByUserIdAndBookingId(long userId, long bookingId);

    List<BookingDto> findAllByUserId(long bookingId, int from, int size);

    List<BookingDto> findAllByOwnerId(long ownerId, int from, int size);

    List<BookingDto> getByUserIdAndByState(long userId, String state, int from, int size);

    List<BookingDto> getOwnerIdAndByState(long ownerId, String state, int from, int size);

}
