package ru.practicum.shareit.booking;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * TODO Sprint add-bookings.
 */
@RequiredArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final long id;
    private final LocalDateTime start;
    private final LocalDateTime end;
    @Column(name = "item_id")
    private final long itemId;
    @Column(name = "user_id")
    private final long userId;
    private final BookingStatus status;

}
