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
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.item.exception.AccessException;

import java.util.List;

@Slf4j
@Service
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository, UserRepository userRepository) {
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
    }

    public ItemDto addItem(ItemDto itemDto, Long userId) {
        if (!userRepository.containsUser(userId)) {
            throw new AccessException();
        }
        itemDto.setId(null);
        Item created =
            itemRepository.save(ItemMapper.toEntity(itemDto, userId), userId).orElseThrow(RepositoryException::new);
        return ItemMapper.toDto(created);
    }

    public ItemDto editItem(ItemDto itemDto, Long userId, Long itemId) {
        if (!userRepository.containsUser(userId)) {
            throw new AccessException();
        }
        if (!itemRepository.containsItem(itemId)){
            throw new EntityNotFoundException();
        }
        itemDto.setId(itemId);
        Item updated =
            itemRepository.save(ItemMapper.toEntity(itemDto, userId), userId).orElseThrow(EntityNotFoundException::new);
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
