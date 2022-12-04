package ru.practicum.shareit.request;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.shareit.item.ItemDto;
import ru.practicum.shareit.user.UserDto;

import java.time.LocalDateTime;
import java.util.List;

@JsonTest
public class ItemRequestDtoJsonTest {

    @Autowired
    JacksonTester<ItemRequestDto> json;

    @Test
    void test() throws Exception {
        ItemDto i = new ItemDto(1L, "text", "desc", true, 1L, 1L);
        UserDto u = new UserDto(1L, "name", "user@email.com");
        ItemRequestDto r = new ItemRequestDto(
                1L,
                "desc",
                u,
                LocalDateTime.of(2022, 10, 10, 10, 10, 10),
                List.of(i)
        );

        JsonContent<ItemRequestDto> result = json.write(r);

        result.assertThat().extractingJsonPathNumberValue("$.id").isEqualTo(1);
        result.assertThat().extractingJsonPathStringValue("$.description").isEqualTo("desc");
        result.assertThat().extractingJsonPathStringValue("$.created").isEqualTo(
                LocalDateTime.of(2022, 10, 10, 10, 10, 10).toString()
        );
    }

}
