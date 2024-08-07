package ru.practicum.shareit.item.dto;

import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.validation.NullableNotBlankConstraint;
import ru.practicum.shareit.user.model.User;

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
    private User user;
    private Booking lastBooking;
    private Booking nextBooking;
    @Builder.Default
    private Set<String> comments = new HashSet<>();
}
