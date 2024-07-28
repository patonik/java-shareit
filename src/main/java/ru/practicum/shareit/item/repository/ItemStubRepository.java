package ru.practicum.shareit.item.repository;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.model.Item;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Repository
public class ItemStubRepository {
    private final Map<Long, Item> itemStorage = new HashMap<>();
    private final Map<Long, Set<Long>> userItemIndex = new HashMap<>();

    private long generatedId = 1;


    public Item save(Item item, Long userId) {
        if (!userItemIndex.containsKey(userId)) {
            userItemIndex.put(userId, new HashSet<>());
        }
        Long itemId = item.getId();
        if (itemId == null) {
            item.setId(generatedId++);
        } else if (!itemStorage.containsKey(itemId)) {
            return null;
        }
        itemId = item.getId();
        itemStorage.put(itemId, item);
        userItemIndex.get(userId).add(itemId);
        return item;
    }

    public Item getById(Long itemId) {
        if (!itemStorage.containsKey(itemId)) {
            return null;
        }
        return itemStorage.get(itemId);
    }

    public List<Item> findByUserId(Long userId) {
        return userItemIndex.get(userId).stream().map(itemStorage::get)
            .toList();
    }

    public List<Item> findByNameAndDescriptionAndAvailable(String text) {
        return itemStorage.values().stream()
            .filter(x -> (x.getName().toLowerCase().contains(text.toLowerCase()) ||
                x.getDescription().toLowerCase().contains(text.toLowerCase())) &&
                x.getAvailable().equals(true)
            )
            .toList();

    }

    public boolean containsUser(Long userId) {
        return userItemIndex.containsKey(userId);
    }

    public boolean containsItem(Long itemId) {
        return itemStorage.containsKey(itemId);
    }

    public boolean userPossessesItem(Long userId, Long itemId) {
        return userItemIndex.containsKey(userId) && userItemIndex.get(userId).contains(itemId);
    }
}
