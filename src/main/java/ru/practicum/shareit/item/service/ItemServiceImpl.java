package ru.practicum.shareit.item.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.item.exception.EntityNotFoundException;
import ru.practicum.shareit.item.exception.RepositoryException;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

@Slf4j
@Service
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public ItemDto addItem(ItemDto itemDto, Long userId) {
        itemDto.setId(null);
        Item created = itemRepository.save(ItemMapper.toEntity(itemDto, userId)).orElseThrow(RepositoryException::new);
        return ItemMapper.toDto(created);
    }

    public ItemDto editItem(ItemDto itemDto, Long userId) {
        Item updated = itemRepository.save(ItemMapper.toEntity(itemDto, userId)).orElseThrow(EntityNotFoundException::new);
        return ItemMapper.toDto(updated);
    }

    public ItemDto getItem(Long itemId) {
        Item found = itemRepository.getById(itemId).orElseThrow(EntityNotFoundException::new);
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
