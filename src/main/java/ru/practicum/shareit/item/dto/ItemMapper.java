package ru.practicum.shareit.item.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.practicum.shareit.item.model.Item;

@Mapper(componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ItemMapper {
    ItemDto toDto(Item item);

    @Mapping(target = "ownerId", source = "userId")
    Item toEntity(ItemDto itemDto, Long userId);

    @Mapping(target = "ownerId", ignore = true)
    Item updateEntity(@MappingTarget Item item, ItemDto itemDto);
}
