package ru.practicum.shareit.booking;


import java.util.List;

public interface BookingService {

    BookingDto add(long userId, BookingDtoIdOnly b);

    BookingDto changeStatus(long userId, long bookingId, boolean isApproved);

    BookingDto getByUserIdAndBookingId(long userId, long bookingId);

    List<BookingDto> findAllByUserId(long bookingId);

    List<BookingDto> findAllByOwnerId(long ownerId);

    List<BookingDto> getUserIdAndByState(long userId, String state);

    List<BookingDto> getOwnerIdAndByState(long ownerId, String state);

}
