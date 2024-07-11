package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

/**
 * TODO Sprint add-controllers.
 */
@Data
@Builder
public class ItemDto {
    private Long id;
    @NotNull
    private String name;
    private String description;
    @Builder.Default
    private Boolean available = true;
}
