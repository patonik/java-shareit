package ru.practicum.shareit.request.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * TODO Sprint add-item-requests.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemRequestDto {
    private Long id;
    @NotBlank
    @Size(min = 1, max = 512)
    private String description;
    private LocalDateTime created;
    @Builder.Default
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Object> items = new HashSet<>();
}
