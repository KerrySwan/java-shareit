package ru.practicum.shareit.server.booking;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findAllByBookerId(long bookerId, Pageable pageable);

    @Query("select " +
            " b " +
            "from Booking b " +
            "where 1=1 " +
            "  and b.item.ownerId = :ownerId ")
    List<Booking> findAllByItemOwnerId(long ownerId, Pageable pageable);

    List<Booking> findAllByBookerIdAndStartIsAfter(long bookerId, LocalDateTime start, Pageable pageable);

    List<Booking> findAllByBookerIdAndEndIsBefore(long bookerId, LocalDateTime end, Pageable pageable);

    @Query("select " +
            " b " +
            "from Booking b " +
            "where 1=1" +
            "  and b.booker.id = :bookerId " +
            "  and :now >= b.start " +
            "  and :now <= b.end")
    List<Booking> findAllByBookerIdAndStartIsBeforeAndEndIsAfter(
            long bookerId,
            @Param("now") LocalDateTime now,
            Pageable pageable
    );

    List<Booking> findAllByBookerIdAndStatus(long bookerId, BookingStatus status, Pageable pageable);

    @Query("select " +
            " b " +
            "from Booking b " +
            "where 1=1" +
            "  and b.item.ownerId = :ownerId " +
            "  and :start <= b.start ")
    List<Booking> findAllByOwnerIdAndStartIsAfter(long ownerId, LocalDateTime start, Pageable pageable);

    @Query("select " +
            " b " +
            "from Booking b " +
            "where 1=1" +
            "  and b.item.ownerId = :ownerId " +
            "  and :end > b.end ")
    List<Booking> findAllByOwnerIdAndEndIsBefore(long ownerId, LocalDateTime end, Pageable pageable);

    @Query("select " +
            " b " +
            "from Booking b " +
            "where 1=1" +
            "  and b.item.ownerId = :ownerId " +
            "  and :now >= b.start " +
            "  and :now <= b.end")
    List<Booking> findAllByOwnerIdAndStartIsBeforeAndEndIsAfter(
            long ownerId,
            @Param("now") LocalDateTime now,
            Pageable pageable
    );

    @Query("select " +
            " b " +
            "from Booking b " +
            "where 1=1" +
            "  and b.item.ownerId = :ownerId " +
            "  and b.status = :status")
    List<Booking> findAllByOwnerIdAndStatus(long ownerId, BookingStatus status, Pageable pageable);

    @Query("select " +
            " b " +
            "from Booking b " +
            "where 1=1" +
            "  and b.item.id = :itemId" +
            "  and b.booker.id != :bookerId"
            + "  and b.status != 'REJECTED' "
    )
    List<Booking> findAllByItemId(long itemId, long bookerId, Pageable pageable);

    @Query("select " +
            " b " +
            "from Booking b " +
            "where 1=1" +
            "  and b.item.id = :itemId" +
            "  and b.booker.id = :bookerId" +
            "  and b.status = 'APPROVED' " +
            "  and b.end <= :now"
    )
    List<Booking> findAllByItemIdAndBookerId(long itemId, long bookerId, LocalDateTime now, Pageable pageable);

}
