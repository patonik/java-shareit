package ru.practicum.shareit.item.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import java.util.Optional;

import static io.micrometer.common.util.StringUtils.isBlank;

/**
 * TODO Sprint add-controllers.
 */
@Data
@Builder
public class ItemDto {
    private Long id;
    private String name;
    private String description;
    private Boolean available;

    @JsonIgnore
    public Optional<Long> getIdIfExists() {
        return Optional.ofNullable(id);
    }

    @JsonIgnore
    public Optional<String> getNameIfExists() {
        if (isBlank(name)) {
            return Optional.empty();
        }
        return Optional.of(name);
    }

    @JsonIgnore
    public Optional<String> getDescriptionIfExists() {
        if (isBlank(description)) {
            return Optional.empty();
        }
        return Optional.of(description);
    }

    @JsonIgnore
    public Optional<Boolean> getAvailableIfExists() {
        return Optional.ofNullable(available);
    }
}
