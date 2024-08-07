package ru.practicum.shareit.user.dto;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.practicum.shareit.user.model.User;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {
    UserDto toDto(User user);

    User toEntity(UserDto userDto);

    User updateEntity(UserDto userDto, @MappingTarget User user);
}
