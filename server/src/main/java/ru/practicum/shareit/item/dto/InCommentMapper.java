package ru.practicum.shareit.item.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface InCommentMapper {
    @Mapping(target = "item", source = "item")
    @Mapping(target = "author", source = "user")
    @Mapping(target = "id", ignore = true)
    Comment toEntity(InCommentDto inCommentDto, Item item, User user);
}
