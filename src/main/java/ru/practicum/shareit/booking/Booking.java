package ru.practicum.shareit.booking;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.common.exception.InvalidDateException;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private LocalDateTime start;
    @Column(name = "\"end\"")
    private LocalDateTime end;
    @OneToOne
    @JoinColumn(
            name = "item_id",
            referencedColumnName = "id"
    )
    private Item item;
    @OneToOne
    @JoinColumn(
            name = "booker_id",
            referencedColumnName = "id"
    )
    private User booker;
    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    public Booking(final long id, final LocalDateTime start, final LocalDateTime end, final Item item, final User booker, final BookingStatus status) {
        if (end.isBefore(start)) throw new InvalidDateException("start should be before end");
        if (start.isBefore(LocalDateTime.now())) throw new InvalidDateException("start should not be in the past");
        this.id = id;
        this.start = start;
        this.end = end;
        this.item = item;
        this.booker = booker;
        this.status = status;
    }

    public Booking() {
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public void setStart(LocalDateTime start) {
        if (start.isBefore(LocalDateTime.now())) throw new InvalidDateException("start time cannot be in the past");
        if (end != null && start.isAfter(this.getEnd()))
            throw new InvalidDateException("start time cannot be after end time");
        this.start = start;
    }
}
