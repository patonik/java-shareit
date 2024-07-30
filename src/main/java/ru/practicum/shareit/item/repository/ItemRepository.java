package ru.practicum.shareit.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.item.model.Item;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Optional<Item> findByUserIdAndId(Long userId, Long itemId);

    List<Item> findAllByUserId(Long userId);

    @Query("select i from Item i where (LOWER(i.name) like CONCAT('%', CONCAT(LOWER(:text), '%')) OR LOWER(i.description) like CONCAT('%', CONCAT(LOWER(:text), '%'))) AND i.available = TRUE")
    List<Item> findByNameOrDescriptionAndAvailable(String text);
}
