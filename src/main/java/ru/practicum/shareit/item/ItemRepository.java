package ru.practicum.shareit.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query(value =
            "select" +
                    " i " +
                    "from Item i " +
                    "where (lower(i.name) like :pattern or lower(i.description) like :pattern)" +
                    "  and i.available is true" +
                    "  and length(:pattern) > 2")
    List<Item> findAllByNameOrByDesc(@Param("pattern") String pattern);

    void deleteById(long id);

}
