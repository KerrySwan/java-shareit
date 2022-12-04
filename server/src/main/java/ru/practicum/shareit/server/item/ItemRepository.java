package ru.practicum.shareit.server.item;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query(value = "select * from items i " +
            "where 1 = 1" +
            "  and (lower(i.name) like lower(concat('%', ?1,'%')) or lower(i.description) like lower(concat('%', ?1,'%')))" +
            "  and i.available = true" +
            "  and length(?1) > 0",
            nativeQuery = true)
    List<Item> findAllByNameOrByDesc(String pattern, Pageable pageable);

    void deleteById(long id);

}
