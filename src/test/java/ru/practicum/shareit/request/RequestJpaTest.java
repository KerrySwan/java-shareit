package ru.practicum.shareit.request;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Sort;
import ru.practicum.shareit.config.PersistenceConfig;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@DataJpaTest
@Import(PersistenceConfig.class)
public class RequestJpaTest {

    private UserRepository userRepository;
    private ItemRepository itemRepository;
    private ItemRequestRepository itemRequestRepository;

    Sort descSortByCreation = Sort.by(Sort.Direction.DESC, "created");

    private final User u = new User(1L, "name1", "user1@email.com");
    private final Item i = new Item(1L, "text", "desc", true, 1L, 1L);
    private final ItemRequest r = new ItemRequest(1L, "desc", u, LocalDateTime.now(), List.of(i));

    @Autowired
    public RequestJpaTest(UserRepository userRepository, ItemRepository itemRepository, ItemRequestRepository itemRequestRepository) {
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
        this.itemRequestRepository = itemRequestRepository;
        userRepository.save(u);
        itemRepository.save(i);
        itemRequestRepository.save(r);
    }


    @Test
    void verifyFindAllByRequesterId() {
        Assertions.assertEquals(1, itemRequestRepository.findAllByRequesterId(1L, descSortByCreation).size());
    }

}
