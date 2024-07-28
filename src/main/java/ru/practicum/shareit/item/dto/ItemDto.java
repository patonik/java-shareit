package ru.practicum.shareit.item.dto;

import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.item.validation.NullableNotBlankConstraint;
import ru.practicum.shareit.user.model.User;


/**
 * TODO Sprint add-controllers.
 */
@Data
@Builder
public class ItemDto {
    private Long id;
    @NullableNotBlankConstraint
    private String name;
    @NullableNotBlankConstraint
    private String description;
    private Boolean available;
    @Builder.Default
    private User user = new User();
}
