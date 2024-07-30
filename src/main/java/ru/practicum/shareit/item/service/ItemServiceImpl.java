package ru.practicum.shareit.item.service;

import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.AccessException;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.exception.EntityNotFoundException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.repository.UserRepository;
import ru.practicum.shareit.exception.MissingValueException;
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
        userRepository.findById(userId).orElseThrow(AccessException::new);
        itemDto.setId(null);
        Item created =
            itemRepository.save(itemMapper.toEntity(itemDto, userId));
        return itemMapper.toDto(created);
    }

    public ItemDto editItem(ItemDto itemDto, Long userId, Long itemId) {
        Item item = itemRepository.findByUserIdAndId(userId, itemId).orElseThrow(EntityNotFoundException::new);
        itemDto.setId(itemId);
        Item updated =
            itemRepository.save(itemMapper.updateEntity(item, itemDto));
        return itemMapper.toDto(updated);
    }

    public ItemDto getItem(Long itemId) {
        Item found = itemRepository.findById(itemId).orElseThrow(EntityNotFoundException::new);
        return itemMapper.toDto(found);
    }

    public List<ItemDto> getItems(Long userId) {
        List<Item> found = itemRepository.findAllByUserId(userId);
        return found.stream().map(itemMapper::toDto).toList();
    }

    public List<ItemDto> findItems(String text) {
        if (StringUtils.isBlank(text)) {
            return Collections.emptyList();
        }
        List<Item> found = itemRepository.findByNameOrDescriptionAndAvailable(text);
        return found.stream().map(itemMapper::toDto).toList();
    }
}
