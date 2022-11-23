package ru.practicum.shareit.request;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;
import java.util.List;

@JsonTest
public class ItemRequestJsonTest {

    @Autowired
    JacksonTester<ItemRequest> json;

    @Test
    void test() throws Exception{
        Item i = new Item(1L, "text", "desc", true, 1L, 1L);
        User u = new User(1L, "name", "user@email.com");
        ItemRequest r = new ItemRequest(
                1L,
                "desc",
                u,
                LocalDateTime.of(2022, 10, 10, 10, 10, 10),
                List.of(i)
        );

        JsonContent<ItemRequest> result = json.write(r);

        result.assertThat().extractingJsonPathNumberValue("$.id").isEqualTo(1);
        result.assertThat().extractingJsonPathStringValue("$.description").isEqualTo("desc");
        result.assertThat().extractingJsonPathStringValue("$.created").isEqualTo(
                LocalDateTime.of(2022, 10, 10, 10, 10, 10).toString()
        );
    }

}
