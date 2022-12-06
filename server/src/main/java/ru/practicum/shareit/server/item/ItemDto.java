package ru.practicum.shareit.server.item;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.server.booking.BookingDtoIdOnly;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class ItemDto {

    private long id;

    private String name;

    private String description;

    private Boolean available;
    private long ownerId;
    private long requestId;
    private BookingDtoIdOnly lastBooking;
    private BookingDtoIdOnly nextBooking;
    private List<CommentDto> comments;

    @JsonCreator
    public ItemDto(long id, String name, String description, Boolean available, long ownerId, long requestId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.available = available;
        this.ownerId = ownerId;
        this.requestId = requestId;
    }

    public void setNextBooking(BookingDtoIdOnly nextBooking) {
        if (this.lastBooking == null) this.lastBooking = nextBooking;
        else this.lastBooking = this.nextBooking;
        this.nextBooking = nextBooking;
    }

}
