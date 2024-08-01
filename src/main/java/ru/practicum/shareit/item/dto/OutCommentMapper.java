package ru.practicum.shareit.item.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.shareit.item.model.Comment;

@Mapper(componentModel = "spring")
public interface OutCommentMapper {
    @Mapping(target = "authorName", source = "author.name")
    OutCommentDto toDto(Comment comment);
}
