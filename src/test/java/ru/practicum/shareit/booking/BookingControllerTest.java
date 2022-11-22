package ru.practicum.shareit.booking;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;

import static ru.practicum.shareit.booking.BookingStatus.APPROVED;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookingControllerTest {

    @Autowired
    BookingController bookingController;

    private User u = new User(1L, "name", "user@email.com");
    private Item i = new Item(1L, "text", "desc", true, 1L, 1L);
    private BookingDto bookingDto = new BookingDto(
            1L,
            LocalDateTime.of(2023, 1, 1, 1, 1, 1),
            LocalDateTime.of(2023, 3, 1, 1, 1, 1),
            i,
            u,
            APPROVED
    );

    @Test


}
