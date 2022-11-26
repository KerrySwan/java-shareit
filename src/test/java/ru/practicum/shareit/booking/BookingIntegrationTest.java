package ru.practicum.shareit.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.practicum.shareit.common.exception.AccessException;
import ru.practicum.shareit.common.exception.InvalidUserException;
import ru.practicum.shareit.common.exception.UnsupportedStatusException;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.ItemController;
import ru.practicum.shareit.item.ItemMapper;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserController;
import ru.practicum.shareit.user.UserMapper;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.practicum.shareit.booking.BookingStatus.APPROVED;
import static ru.practicum.shareit.booking.BookingStatus.WAITING;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookingIntegrationTest {

    @Autowired
    BookingController bookingController;
    @Autowired
    ItemController itemController;
    @Autowired
    UserController userController;

    private final User u1 = new User(1L, "name1", "user1@email.com");
    private final User u2 = new User(2L, "name2", "user2@email.com");
    private final Item i = new Item(1L, "text", "desc", true,  1L, 1L);
    private final Booking b = new Booking(
            1L,
            LocalDateTime.of(2023, 1, 1, 1, 1, 1),
            LocalDateTime.of(2023, 3, 1, 1, 1, 1),
            i,
            u2,
            WAITING
    );

    @BeforeEach
    void preparation() {
        userController.create(UserMapper.toDto(u1));
        userController.create(UserMapper.toDto(u2));
        itemController.create(1L, ItemMapper.toDto(i));
        bookingController.add(2L, BookingMapper.toDtoIdOnly(b));
    }

    @Test
    void createBooking() {
        assertEquals(1, bookingController.getAllById(2L, 0, 1).get(0).getId());
    }

    @Test
    void createBookingInvalidUserException() {
        assertThrows(
                InvalidUserException.class,
                () -> bookingController.add(1L, BookingMapper.toDtoIdOnly(b))
        );
    }

    @Test
    void changeStatus() {
        bookingController.changeStatus(1L, 1L, true);
        assertEquals(APPROVED, bookingController.getAllById(2L, 0, 1).get(0).getStatus());
    }

    @Test
    void changeStatusAccessException() {
        bookingController.changeStatus(1L, 1L, true);
        assertThrows(
                AccessException.class,
                () -> bookingController.changeStatus(1L, 1L, false)
        );

    }

    @Test
    void getByUserIdAndBookingId() {
        assertEquals(1, bookingController.getByUserIdAndBookingId(2L, 1L).getId());
    }

    @Test
    void getAllById() {
        assertEquals(1, bookingController.getAllById(2L, 0, 1).size());
    }

    @Test
    void getAllByOwner() {
        assertEquals(1, bookingController.getAllByOwner(1L, 0, 1).size());
    }

    @Test
    void getByState() {
        assertEquals(1, bookingController.getByState(2L, "ALL", 0, 1).size());
        assertEquals(0, bookingController.getByState(2L, "PAST", 0, 1).size());
        assertEquals(0, bookingController.getByState(2L, "CURRENT", 0, 1).size());
        assertEquals(1, bookingController.getByState(2L, "FUTURE", 0, 1).size());
        assertEquals(1, bookingController.getByState(2L, "WAITING", 0, 1).size());
        assertEquals(0, bookingController.getByState(2L, "REJECTED", 0, 1).size());
    }

    @Test
    void getByOwnerAndByState() {
        assertEquals(1, bookingController.getByOwnerState(1L, "ALL", 0, 1).size());
        assertEquals(0, bookingController.getByOwnerState(1L, "PAST", 0, 1).size());
        assertEquals(0, bookingController.getByOwnerState(1L, "CURRENT", 0, 1).size());
        assertEquals(1, bookingController.getByOwnerState(1L, "FUTURE", 0, 1).size());
        assertEquals(1, bookingController.getByOwnerState(1L, "WAITING", 0, 1).size());
        assertEquals(0, bookingController.getByOwnerState(1L, "REJECTED", 0, 1).size());
    }

    @Test
    void getgetByOwnerStateWithException() {
        assertThrows(
                UnsupportedStatusException.class,
                () -> bookingController.getByOwnerState(1L, "ERROR", 0, 1)
        );
    }

}
