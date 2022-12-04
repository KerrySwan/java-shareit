package ru.practicum.shareit.item;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.practicum.shareit.common.exception.DoesNotExistsException;
import ru.practicum.shareit.common.exception.ItemIsAnavailableException;
import ru.practicum.shareit.user.UserController;
import ru.practicum.shareit.user.UserDto;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ItemIntegrationTest {

    @Autowired
    private ItemController itemController;
    @Autowired
    private UserController userController;

    private final UserDto userDto = new UserDto(
            1L,
            "name",
            "email@email.com"
    );

    private final UserDto userDto2 = new UserDto(
            2L,
            "name2",
            "email2@email.com"
    );

    private final ItemDto itemDto = new ItemDto(
            1L,
            "name",
            "desc",
            true,
            1L,
            1L
    );

    private final CommentDto commentDto = new CommentDto(
            1L,
            "text",
            1L,
            "name"
    );

    @BeforeEach
    void preparation() {
        userController.create(userDto);
    }

    @Test
    void create() {
        itemController.create(1L, itemDto);
        assertEquals(itemController.get(1L, 1L).getId(), itemDto.getId());
    }

    @Test
    void update() {
        create();
        ItemDto upd = new ItemDto(
                1L,
                "upd",
                "upd",
                true,
                1L,
                1L
        );
        itemController.update(1L, 1L, upd);
        assertEquals(itemController.get(1L, 1L).getName(), upd.getName());
    }

    @Test
    void updateThrowsDoesNotExistsException() {
        create();
        userController.create(userDto2);
        ItemDto upd = new ItemDto(
                1L,
                "upd",
                "upd",
                true,
                1L,
                1L
        );
        assertThrows(
                DoesNotExistsException.class,
                () -> itemController.update(1L, 2L, upd)
        );
    }

    @Test
    void search() {
        create();
        assertEquals(itemController.search("desc", 0, 10).get(0).getName(), itemDto.getName());
    }

    @Test
    void getWithException() {
        assertThrows(NoSuchElementException.class, () -> itemController.get(1L, 1L));
    }

    @Test
    void updateWithException() {
        assertThrows(NoSuchElementException.class, () -> itemController.update(12L, 12L, itemDto));
    }

    @Test
    void commentException() {
        itemController.create(1L, itemDto);
        assertThrows(
                ItemIsAnavailableException.class,
                () -> itemController.addComment(1L, 1L, commentDto)
        );
    }

}
