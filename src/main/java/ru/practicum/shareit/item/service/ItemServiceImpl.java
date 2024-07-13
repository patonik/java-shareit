package ru.practicum.shareit.item.service;

import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.item.exception.AccessException;
import ru.practicum.shareit.item.exception.EntityNotFoundException;
import ru.practicum.shareit.item.exception.RepositoryException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.exception.DataOperationException;
import ru.practicum.shareit.user.exception.MissingValueException;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final ItemMapper itemMapper;

    public ItemDto addItem(ItemDto itemDto, Long userId) {
        if (itemDto.getName() == null ||
            itemDto.getDescription() == null ||
            itemDto.getAvailable() == null) {
            throw new MissingValueException();
        }
        if (!userRepository.containsUser(userId)) {
            throw new AccessException();
        }
        itemDto.setId(null);
        Item created =
            itemRepository.save(itemMapper.toEntity(itemDto, userId), userId);
        if (created == null) {
            throw new RepositoryException();
        }
        return itemMapper.toDto(created);
    }

    public ItemDto editItem(ItemDto itemDto, Long userId, Long itemId) {
        if (!userRepository.containsUser(userId) || !itemRepository.userPossessesItem(userId, itemId)) {
            throw new AccessException();
        }
        Item item = itemRepository.getById(itemId);
        if (item == null) {
            throw new EntityNotFoundException();
        }
        itemDto.setId(itemId);
        Item updated =
            itemRepository.save(itemMapper.updateEntity(item, itemDto), userId);
        if (updated == null) {
            throw new DataOperationException();
        }
        return itemMapper.toDto(updated);
    }

    public ItemDto getItem(Long itemId) {
        Item found = itemRepository.getById(itemId);
        if (found == null) {
            throw new EntityNotFoundException();
        }
        return itemMapper.toDto(found);
    }

    public List<ItemDto> getItems(Long userId) {
        List<Item> found = itemRepository.findByUserId(userId);
        return found.stream().map(itemMapper::toDto).toList();
    }

    public List<ItemDto> findItems(String text) {
        if (StringUtils.isBlank(text)) {
            return Collections.emptyList();
        }
        List<Item> found = itemRepository.findByNameAndDescriptionAndAvailable(text);
        return found.stream().map(itemMapper::toDto).toList();
    }
}
