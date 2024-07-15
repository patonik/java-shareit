package ru.practicum.shareit.user.dto;

import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.user.validation.NullableNotBlankConstraint;

@Data
@Builder
public class UserDto {
    private Long id;
    @NullableNotBlankConstraint
    private String name;
    @Email
    private String email;
}
