package ru.practicum.shareit.booking.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

@Mapper(componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface InwardBookingMapper {
    @Mapping(target = "status", expression = "java(ru.practicum.shareit.booking.model.Status.WAITING)")
    @Mapping(target = "item", source = "item")
    @Mapping(target = "booker", source = "user")
    @Mapping(target = "id", ignore = true)
    Booking toEntity(InwardBookingDto inwardBookingDto, Item item, User user);
}
