package ru.practicum.shareit.item.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.model.Item;

import java.util.Set;

@Mapper(componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ItemMapper {
    @Mapping(target = "user", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
    @Mapping(target = "id", source = "item.id")
    @Mapping(target = "lastBooking", source = "last", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
    @Mapping(target = "nextBooking", source = "next", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
    @Mapping(target = "comments", source = "comments", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
    ItemDto toDto(Item item, Booking last, Booking next, Set<String> comments);

    @Mapping(target = "user.id", source = "userId")
    Item toEntity(ItemDto itemDto, Long userId);

    @Mapping(target = "user", ignore = true)
    Item updateEntity(@MappingTarget Item item, ItemDto itemDto);
}
