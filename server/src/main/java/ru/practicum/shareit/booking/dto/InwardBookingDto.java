package ru.practicum.shareit.booking.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * TODO Sprint add-bookings.
 */
@Data
@Builder
public class InwardBookingDto {
    private LocalDateTime start;
    private LocalDateTime end;
    private Long itemId;
}
