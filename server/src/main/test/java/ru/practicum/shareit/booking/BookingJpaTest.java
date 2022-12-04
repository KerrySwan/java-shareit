package ru.practicum.shareit.booking;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import ru.practicum.shareit.config.PersistenceConfig;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.shareit.booking.BookingStatus.APPROVED;


@DataJpaTest
@Import(PersistenceConfig.class)
public class BookingJpaTest {

    private final UserRepository userRepository;

    private final ItemRepository itemRepository;

    private final BookingRepository bookingRepository;

    private final User u1 = new User(1L, "name1", "user1@email.com");
    private final User u2 = new User(2L, "name2", "user2@email.com");
    private final Item i = new Item(1L, "text", "desc", true, 1L, 1L);
    private final Booking b = new Booking(
            1L,
            LocalDateTime.of(2023, 1, 1, 1, 1, 1),
            LocalDateTime.of(2023, 3, 1, 1, 1, 1),
            i,
            u2,
            APPROVED
    );

    @Autowired
    public BookingJpaTest(UserRepository userRepository, ItemRepository itemRepository, BookingRepository bookingRepository) {
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
        this.bookingRepository = bookingRepository;
        userRepository.save(u1);
        userRepository.save(u2);
        itemRepository.save(i);
        bookingRepository.save(b);
    }


    @Test
    public void verifyFindAllByBookerId() {
        List<Booking> bookingList = bookingRepository.findAllByBookerId(2, PageRequest.of(0, 10));
        Assertions.assertEquals(1, bookingList.size());
    }

    @Test
    public void verifyFindAllByOwnerId() {
        List<Booking> bookingList = bookingRepository.findAllByItemOwnerId(1, PageRequest.of(0, 10));
        Assertions.assertEquals(1, bookingList.size());
    }

    @Test
    public void verifyFindAllByBookerIdAndStartIsAfter() {
        List<Booking> bookingList =
                bookingRepository.findAllByBookerIdAndStartIsAfter(
                        2,
                        LocalDateTime.of(2022, 10, 10, 10, 10, 10),
                        PageRequest.of(0, 10)
                );
        Assertions.assertEquals(1, bookingList.size());
    }

    @Test
    public void verifyFindAllByBookerIdAndEndIsBefore() {
        List<Booking> bookingList =
                bookingRepository.findAllByBookerIdAndEndIsBefore(
                        2,
                        LocalDateTime.of(2024, 10, 10, 10, 10, 10),
                        PageRequest.of(0, 10)
                );
        Assertions.assertEquals(1, bookingList.size());
    }

    @Test
    public void verifyFindAllByBookerIdAndStartIsBeforeAndEndIsAfter() {
        List<Booking> bookingList =
                bookingRepository.findAllByBookerIdAndStartIsBeforeAndEndIsAfter(
                        2,
                        LocalDateTime.of(2023, 2, 10, 10, 10, 10),
                        PageRequest.of(0, 10)
                );
        Assertions.assertEquals(1, bookingList.size());
    }

    @Test
    public void verifyFindAllByBookerIdAndStatus() {
        List<Booking> bookingList =
                bookingRepository.findAllByBookerIdAndStatus(
                        2,
                        APPROVED,
                        PageRequest.of(0, 10)
                );
        Assertions.assertEquals(1, bookingList.size());
    }

    @Test
    public void verifyFindAllByOwnerIdAndStartIsAfter() {
        List<Booking> bookingList =
                bookingRepository.findAllByOwnerIdAndStartIsAfter(
                        1,
                        LocalDateTime.of(2021, 10, 10, 10, 10, 10),
                        PageRequest.of(0, 10)
                );
        Assertions.assertEquals(1, bookingList.size());
    }

    @Test
    public void verifyFindAllByOwnerIdAndEndIsBefore() {
        List<Booking> bookingList =
                bookingRepository.findAllByOwnerIdAndEndIsBefore(
                        1,
                        LocalDateTime.of(2024, 10, 10, 10, 10, 10),
                        PageRequest.of(0, 10)
                );
        Assertions.assertEquals(1, bookingList.size());
    }

    @Test
    public void verifyFindAllByOwnerIdAndStartIsBeforeAndEndIsAfter() {
        List<Booking> bookingList =
                bookingRepository.findAllByOwnerIdAndStartIsBeforeAndEndIsAfter(
                        1,
                        LocalDateTime.of(2023, 2, 10, 10, 10, 10),
                        PageRequest.of(0, 10)
                );
        Assertions.assertEquals(1, bookingList.size());
    }

    @Test
    public void verifyFindAllByOwnerIdAndStatus() {
        List<Booking> bookingList =
                bookingRepository.findAllByOwnerIdAndStatus(
                        1,
                        APPROVED,
                        PageRequest.of(0, 10)
                );
        Assertions.assertEquals(1, bookingList.size());
    }

    @Test
    public void verifyFindAllByItemId() {
        List<Booking> bookingList =
                bookingRepository.findAllByItemId(
                        1,
                        1,
                        PageRequest.of(0, 10)
                );
        Assertions.assertEquals(1, bookingList.size());
    }

    @Test
    public void verifyFindAllByItemIdAndBookerId() {
        List<Booking> bookingList =
                bookingRepository.findAllByItemIdAndBookerId(
                        1,
                        2,
                        LocalDateTime.of(2024, 1, 10, 10, 10, 10),
                        PageRequest.of(0, 10)
                );
        Assertions.assertEquals(1, bookingList.size());
    }


}
