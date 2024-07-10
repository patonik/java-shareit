package ru.practicum.shareit.item.dto;

import ru.practicum.shareit.item.model.Item;

public class ItemMapper {
    public static ItemDto toDto(Item item){
        return ItemDto.builder()
            .id(item.getId())
            .name(item.getName())
            .description(item.getDescription())
            .status(item.getStatus())
            .build();
    }
    public static Item toEntity(ItemDto itemDto, Long userId){
        return Item.builder()
            .id(itemDto.getId())
            .name(itemDto.getName())
            .description(itemDto.getDescription())
            .status(itemDto.getStatus())
            .ownerId(userId)
            .build();
    }
}
