package ru.practicum.shareit.item.dto;

import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.validation.NullableNotBlankConstraint;

import java.util.HashSet;
import java.util.Set;


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
    private Long requestId;
    private Object user;
    private Object lastBooking;
    private Object nextBooking;
    @Builder.Default
    private Set<String> comments = new HashSet<>();
}
