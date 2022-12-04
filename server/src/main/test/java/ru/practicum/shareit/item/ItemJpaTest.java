package ru.practicum.shareit.item;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import ru.practicum.shareit.config.PersistenceConfig;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;

import java.util.List;

@DataJpaTest
@Import(PersistenceConfig.class)
public class ItemJpaTest {

    private final UserRepository userRepository;

    private final ItemRepository itemRepository;

    private final User u = new User(1L, "name1", "user1@email.com");
    private final Item i = new Item(1L, "L-ion King", "Tale about king battery", true, 1L, 1L);

    @Autowired
    public ItemJpaTest(UserRepository userRepository, ItemRepository itemRepository) {
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
        userRepository.save(u);
        itemRepository.save(i);
    }

    @Test
    void verifyFindAllByNamePart() {
        List<Item> itemsList =
                itemRepository.findAllByNameOrByDesc(
                        "ion",
                        PageRequest.of(0, 10)
                );
        Assertions.assertEquals(1, itemsList.size());
    }

    @Test
    void verifyFindAllByDescPart() {
        List<Item> itemsList =
                itemRepository.findAllByNameOrByDesc(
                        "battery",
                        PageRequest.of(0, 10)
                );
        Assertions.assertEquals(1, itemsList.size());
    }

    @Test
    void verifyDeleteById() {
        itemRepository.deleteById(1);
        Assertions.assertEquals(0, itemRepository.findAll().size());
    }

}
