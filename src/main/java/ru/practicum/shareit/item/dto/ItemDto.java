package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.Optional;

/**
 * TODO Sprint add-controllers.
 */
@Data
@Builder
public class ItemDto {
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotNull
    private Boolean available;

    public Optional<Long> getIdIfExists(){
        return Optional.ofNullable(id);
    }
    public Optional<String> getNameIfExists(){
        return Optional.ofNullable(name);
    }
    public Optional<String> getDescriptionIfExists(){
        return Optional.ofNullable(description);
    }
    public Optional<Boolean> getAvailableIfExists(){
        return Optional.ofNullable(available);
    }
}
