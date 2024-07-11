package ru.practicum.shareit.item.dto;

import ru.practicum.shareit.item.model.Item;

public class ItemMapper {
    public static ItemDto toDto(Item item){
        return ItemDto.builder()
            .id(item.getId())
            .name(item.getName())
            .description(item.getDescription())
            .available(item.getAvailable())
            .build();
    }
    public static Item toEntity(ItemDto itemDto, Long userId){
        return Item.builder()
            .id(itemDto.getId())
            .name(itemDto.getName())
            .description(itemDto.getDescription())
            .available(itemDto.getAvailable())
            .ownerId(userId)
            .build();
    }
}
