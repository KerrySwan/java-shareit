package ru.practicum.shareit.booking;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findAllByBookerId(long bookerId, Sort sort);

    @Query("select " +
            " b " +
            "from Booking b " +
            "where 1=1 " +
            "  and b.item.ownerId = :ownerId ")
    List<Booking> findAllByItemOwnerId(long ownerId, Sort sort);

    List<Booking> findAllByBookerIdAndStartIsAfter(long bookerId, LocalDateTime start, Sort sort);

    List<Booking> findAllByBookerIdAndEndIsBefore(long bookerId, LocalDateTime end, Sort sort);

    @Query("select " +
            " b " +
            "from Booking b " +
            "where 1=1" +
            "  and b.booker.id = :bookerId " +
            "  and :now between b.end and b.start")
    List<Booking> findAllByBookerIdAndStartIsBeforeAndEndIsAfter(
            long bookerId,
            @Param("now") LocalDateTime now,
            Sort sort
    );

    List<Booking> findAllByBookerIdAndStatus(long bookerId, BookingStatus status);

    @Query("select " +
            " b " +
            "from Booking b " +
            "where 1=1" +
            "  and b.item.ownerId = :ownerId " +
            "  and :start < b.start ")
    List<Booking> findAllByOwnerIdAndStartIsAfter(long ownerId, LocalDateTime start, Sort sort);

    @Query("select " +
            " b " +
            "from Booking b " +
            "where 1=1" +
            "  and b.item.ownerId = :ownerId " +
            "  and :end > b.end ")
    List<Booking> findAllByOwnerIdAndEndIsBefore(long ownerId, LocalDateTime end, Sort sort);

    @Query("select " +
            " b " +
            "from Booking b " +
            "where 1=1" +
            "  and b.item.ownerId = :ownerId " +
            "  and :now between b.end and b.start ")
    List<Booking> findAllByOwnerIdAndStartIsBeforeAndEndIsAfter(
            long ownerId,
            @Param("now") LocalDateTime now,
            Sort sort
    );

    @Query("select " +
            " b " +
            "from Booking b " +
            "where 1=1" +
            "  and b.item.ownerId = :ownerId " +
            "  and b.status = :status")
    List<Booking> findAllByOwnerIdAndStatus(long ownerId, BookingStatus status);

    @Query("select " +
            " b " +
            "from Booking b " +
            "where 1=1" +
            "  and b.item.id = :itemId" +
            "  and b.booker.id != :bookerId"
            + "  and b.status != 'REJECTED' "
    )
    List<Booking> findAllByItemId(long itemId, long bookerId);

}
