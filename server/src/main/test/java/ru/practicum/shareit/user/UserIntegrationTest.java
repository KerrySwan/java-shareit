package ru.practicum.shareit.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import javax.persistence.EntityNotFoundException;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserIntegrationTest {

    @Autowired
    private UserController userController;

    private UserDto userDto = new UserDto(
            1L,
            "name",
            "email@email.com"
    );

    @Test
    void create() {
        UserDto testUser = userController.create(userDto);
        assertEquals(testUser.getId(), userController.get(1L).getId());
    }

    @Test
    void update() {
        create();
        UserDto updateDto = new UserDto(
                1L,
                "upd",
                "upd@email.com"
        );

        userController.patch(1L, updateDto);
        assertEquals("upd", userController.get(1L).getName());
    }

    @Test
    void delete() {
        create();
        userController.delete(1L);
        assertEquals(0, userController.getAll().size());
    }

    @Test
    void updateWithException() {
        assertThrows(NoSuchElementException.class, () -> userController.patch(12L, userDto));
    }

    @Test
    void getWithException() {
        assertThrows(EntityNotFoundException.class, () -> userController.get(12L));
    }


}
