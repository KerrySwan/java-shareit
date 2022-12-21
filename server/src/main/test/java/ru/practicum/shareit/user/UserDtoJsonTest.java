package ru.practicum.shareit.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import java.io.IOException;

@JsonTest
public class UserDtoJsonTest {

    @Autowired
    JacksonTester<UserDto> json;

    @Test
    public void test() throws IOException {
        UserDto u = new UserDto(1L, "name", "user@email.com");

        JsonContent<UserDto> result = json.write(u);

        result.assertThat().extractingJsonPathNumberValue("$.id").isEqualTo(1);
        result.assertThat().extractingJsonPathStringValue("$.name").isEqualTo("name");
        result.assertThat().extractingJsonPathStringValue("$.email").isEqualTo("user@email.com");
    }

}
