package ru.practicum.shareit.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.practicum.shareit.config.PersistenceConfig;

@DataJpaTest
@Import(PersistenceConfig.class)
public class UserJpaTest {

    private final UserRepository userRepository;

    private final User u = new User(1L, "name1", "user1@email.com");

    @Autowired
    public UserJpaTest(UserRepository userRepository) {
        this.userRepository = userRepository;
        userRepository.save(u);
    }

    @Test
    void verifyDeleteById() {
        Assertions.assertEquals(1, userRepository.findAll().size());
        userRepository.deleteById(1L);
        Assertions.assertEquals(0, userRepository.findAll().size());
    }

}
