package ru.practicum.shareit.user.dto;

import ru.practicum.shareit.user.model.User;

public class UserMapper {
    public static UserDto toDto(User user) {
        return ru.practicum.shareit.user.dto.UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

    public static User toEntity(ru.practicum.shareit.user.dto.UserDto userDto, User user) {
        return User.builder()
                .id(user.getId())
                .name(userDto.getNameIfExists().orElse(user.getName()))
                .email(userDto.getEmailIfExists().orElse(user.getEmail()))
                .build();
    }

    public static User toEntity(ru.practicum.shareit.user.dto.UserDto userDto) {
        return User.builder()
                .name(userDto.getNameIfExists().orElse(""))
                .email(userDto.getEmailIfExists().orElseThrow(RuntimeException::new))
                .build();
    }
}
