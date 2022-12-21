package ru.practicum.shareit.booking;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.user.User;

import java.io.IOException;
import java.time.LocalDateTime;

import static ru.practicum.shareit.booking.BookingStatus.APPROVED;

@JsonTest
public class BookingDtoJsonTest {

    @Autowired
    JacksonTester<BookingDto> json;

    @Test
    public void test() throws IOException {
        User u = new User(1L, "name", "user@email.com");
        Item i = new Item(1L, "text", "desc", true, 1L, 1L);
        BookingDto b = new BookingDto(
                1L,
                LocalDateTime.of(2023, 1, 1, 1, 1, 1),
                LocalDateTime.of(2023, 3, 1, 1, 1, 1),
                i,
                u,
                APPROVED
        );

        JsonContent<BookingDto> result = json.write(b);

        result.assertThat().extractingJsonPathNumberValue("$.id").isEqualTo(1);
        result.assertThat().extractingJsonPathStringValue("$.start")
                .isEqualTo(LocalDateTime.of(2023, 1, 1, 1, 1, 1).toString());
        result.assertThat().extractingJsonPathStringValue("$.end")
                .isEqualTo(LocalDateTime.of(2023, 3, 1, 1, 1, 1).toString());
        result.assertThat().extractingJsonPathStringValue("$.status").isEqualTo(APPROVED.toString());
    }

}
