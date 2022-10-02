package ru.practicum.shareit.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query(value =
            "select" +
            " i.id," +
            " i.name," +
            " i.description," +
            " i.available," +
            " i.owner_id," +
            " i.request_id " +
            "from items i " +
            "where (i.name ~* :pattern or i.description ~* :pattern)" +
            "  and i.available" +
            "  and length(:pattern) > 0",
    nativeQuery = true)
    List<Item> findAllByNameOrByDesc(@Param("pattern") String pattern);

    void deleteById(long id);

}
