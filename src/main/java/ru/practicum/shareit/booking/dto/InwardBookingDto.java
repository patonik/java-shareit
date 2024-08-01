package ru.practicum.shareit.booking.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.booking.validation.ValidBooking;

import java.time.LocalDateTime;

/**
 * TODO Sprint add-bookings.
 */
@Data
@Builder
@ValidBooking
public class InwardBookingDto {
    private LocalDateTime start;
    private LocalDateTime end;
    @NotNull
    private Long itemId;
}
