package ru.practicum.shareit.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import java.io.IOException;

@JsonTest
public class UserJsonTest {

    @Autowired
    JacksonTester<User> json;

    @Test
    public void test() throws IOException {
        User u = new User(1L, "name", "user@email.com");

        JsonContent<User> result = json.write(u);

        result.assertThat().extractingJsonPathNumberValue("$.id").isEqualTo(1);
        result.assertThat().extractingJsonPathStringValue("$.name").isEqualTo("name");
        result.assertThat().extractingJsonPathStringValue("$.email").isEqualTo("user@email.com");
    }

}
