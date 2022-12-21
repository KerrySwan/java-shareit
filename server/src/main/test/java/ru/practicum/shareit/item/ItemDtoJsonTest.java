package ru.practicum.shareit.item;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import java.io.IOException;

@JsonTest
public class ItemDtoJsonTest {

    @Autowired
    JacksonTester<ItemDto> json;

    @Test
    public void test() throws IOException {
        ItemDto i = new ItemDto(1L, "text", "desc", true, 1L, 1L);

        JsonContent<ItemDto> result = json.write(i);

        result.assertThat().extractingJsonPathNumberValue("$.id").isEqualTo(1);
        result.assertThat().extractingJsonPathStringValue("$.name").isEqualTo("text");
        result.assertThat().extractingJsonPathStringValue("$.description").isEqualTo("desc");
        result.assertThat().extractingJsonPathBooleanValue("$.available").isEqualTo(true);
        result.assertThat().extractingJsonPathNumberValue("$.requestId").isEqualTo(1);
    }

}
