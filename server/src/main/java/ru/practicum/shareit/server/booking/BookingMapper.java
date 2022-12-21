package ru.practicum.shareit.server.booking;

public class BookingMapper {

    public static BookingDto toDto(Booking b) {
        return new BookingDto(
                b.getId(),
                b.getStart(),
                b.getEnd(),
                b.getItem(),
                b.getBooker(),
                b.getStatus()
        );
    }

    public static BookingDtoIdOnly toDtoIdOnly(Booking b) {
        return new BookingDtoIdOnly(
                b.getId(),
                b.getStart(),
                b.getEnd(),
                b.getItem().getId(),
                b.getBooker().getId(),
                b.getStatus()
        );
    }

    public static Booking toModel(BookingDto b) {
        return new Booking(
                b.getId(),
                b.getStart(),
                b.getEnd(),
                b.getItem(),
                b.getBooker(),
                b.getStatus()
        );
    }

    public static BookingDto toFullDto(BookingDtoIdOnly b) {
        return new BookingDto(
                b.getId(),
                b.getStart(),
                b.getEnd(),
                b.getStatus()
        );
    }
}
