package ru.practicum.shareit.item;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.model.Item;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Repository
public class ItemRepository {
    private final HashMap<Long, Item> itemStorage = new HashMap<>();
    private long generatedId = 1;

    public Optional<Item> save(Item item) {
        Long itemId = item.getId();
        if (itemId == null) {
            item.setId(generatedId++);
        } else if (!itemStorage.containsKey(itemId)) {
            return Optional.empty();
        }
        itemStorage.put(itemId, item);
        return Optional.of(item);
    }

    public Optional<Item> getById(Long itemId) {
        if (!itemStorage.containsKey(itemId)) {
            return Optional.empty();
        }
        return Optional.of(itemStorage.get(itemId));
    }

    public List<Item> findByUserId(Long userId) {
        return itemStorage.values().stream()
                .filter(x -> x.getOwnerId().equals(userId))
                .toList();
    }

    public List<Item> findByNameAndDescription(String text) {
        return itemStorage.values().stream()
                .filter(x -> x.getName().contains(text) || x.getDescription().contains(text))
                .toList();
    }
}
