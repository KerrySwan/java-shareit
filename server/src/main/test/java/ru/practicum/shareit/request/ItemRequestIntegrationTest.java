package ru.practicum.shareit.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.ItemController;
import ru.practicum.shareit.item.ItemMapper;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserController;
import ru.practicum.shareit.user.UserMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ItemRequestIntegrationTest {

    @Autowired
    private ItemRequestController itemRequestController;
    @Autowired
    private ItemController itemController;
    @Autowired
    private UserController userController;

    private User u1 = new User(1L, "name1", "user1@email.com");
    private User u2 = new User(2L, "name2", "user2@email.com");
    private Item i = new Item(1L, "text", "desc", true, 1L, 1L);
    private ItemRequestDto req = new ItemRequestDto(
            1L,
            "desc",
            UserMapper.toDto(u1),
            LocalDateTime.of(2022, 10, 10, 10, 10, 10),
            new ArrayList<>() {{
                add(ItemMapper.toDto(i));
            }}
    );

    @BeforeEach
    void preparation() {
        userController.create(UserMapper.toDto(u1));
        userController.create(UserMapper.toDto(u2));
        itemController.create(1L, ItemMapper.toDto(i));
        itemRequestController.add(1L, req);
    }

    @Test
    void getByRequestId() {
        assertEquals(itemRequestController.getByRequestId(1L, 1L).getId(), 1);
    }

    @Test
    void getByUserId() {
        assertEquals(itemRequestController.getByUserId(1L).get(0).getId(), 1);
    }

    @Test
    void getAsPage() {
        assertEquals(
                itemRequestController.getAllAsPage(2L, 0, 1).size(),
                1
        );
    }

}
