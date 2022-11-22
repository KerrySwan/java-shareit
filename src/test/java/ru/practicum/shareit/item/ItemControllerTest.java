package ru.practicum.shareit.item;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.practicum.shareit.user.UserController;
import ru.practicum.shareit.user.UserDto;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ItemControllerTest {

    @Autowired
    private ItemController itemController;
    @Autowired
    private UserController userController;

    private UserDto userDto = new UserDto(
            1L,
            "name",
            "email@email.com"
    );

    private ItemDto itemDto = new ItemDto(
            1L,
            "name",
            "desc",
            true,
            1L,
            1L
    );

    private CommentDto commentDto = new CommentDto(
            1L,
            "text",
            1L,
            "name"
    );

    @BeforeEach
    void preparation(){
        userController.create(userDto);
    }

    @Test
    void create(){
        itemController.create(1L, itemDto);
        assertEquals(itemController.get(1L, 1L).getId(), itemDto.getId());
    }

    @Test
    void update(){
        create();
        ItemDto upd = new ItemDto(
                1L,
                "upd",
                "upd",
                true,
                1L,
                1L
        );
        itemController.update(1L,1L, upd);
        assertEquals(itemController.get(1L, 1L).getName(), upd.getName());
    }

    @Test
    void search(){
        create();
        assertEquals(itemController.search("desc").get(0).getName(), itemDto.getName());
    }

    @Test
    void getWithException(){
        assertThrows(NoSuchElementException.class, () -> itemController.get(1L, 1L));
    }

    @Test
    void updateWithException(){
        assertThrows(NoSuchElementException.class, () -> itemController.update(12L, 12L, itemDto));
    }

}
