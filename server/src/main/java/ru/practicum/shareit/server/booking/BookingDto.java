package ru.practicum.shareit.server.booking;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.server.item.Item;
import ru.practicum.shareit.server.user.User;

import java.time.LocalDateTime;

@Getter
@Setter
public class BookingDto {

    private long id;
    private LocalDateTime start;
    private LocalDateTime end;
    private Item item;
    private User booker;
    private BookingStatus status;

    @JsonCreator
    public BookingDto(long id, LocalDateTime start, LocalDateTime end, BookingStatus status) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.status = status;
    }

    public BookingDto(long id, LocalDateTime start, LocalDateTime end, Item item, User booker, BookingStatus status) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.item = item;
        this.booker = booker;
        this.status = status;
    }

}
