package ru.practicum.shareit.item.dto;

import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.item.validation.NullableNotBlankConstraint;


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
}
