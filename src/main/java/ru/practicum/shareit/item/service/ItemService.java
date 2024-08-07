package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.InCommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.OutCommentDto;

import java.util.List;

public interface ItemService {
    ItemDto addItem(ItemDto itemDto, Long userId);

    ItemDto editItem(ItemDto itemDto, Long userId, Long itemId);

    ItemDto getItem(Long itemId, Long userId);

    List<ItemDto> getItems(Long userId);

    List<ItemDto> findItems(String text);

    OutCommentDto addComment(InCommentDto inCommentDto, Long itemId, Long userId);
}
