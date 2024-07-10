package ru.practicum.shareit.item;

import org.springframework.beans.factory.annotation.Autowired;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public class ItemService {
    private final ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public ItemDto addItem(ItemDto itemDto, Long userId) {
        Item created = itemRepository.save(ItemMapper.toEntity(itemDto, userId));
        return ItemMapper.toDto(created);
    }

    public ItemDto editItem(ItemDto itemDto, Long userId) {
        Item updated = itemRepository.save(ItemMapper.toEntity(itemDto, userId));
        return ItemMapper.toDto(updated);
    }

    public ItemDto getItem(Long itemId) {
        Item found = itemRepository.getById(itemId);
        return ItemMapper.toDto(found);
    }

    public List<ItemDto> getItems(Long userId) {
        List<Item> found = itemRepository.findByUserId(userId);
        return found.stream().map(ItemMapper::toDto).toList();
    }

    public List<ItemDto> findItems(String text) {
        List<Item> found = itemRepository.findByNameAndDescription(text);
        return found.stream().map(ItemMapper::toDto).toList();
    }
}
