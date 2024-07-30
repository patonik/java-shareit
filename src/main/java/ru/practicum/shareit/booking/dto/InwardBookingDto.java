package ru.practicum.shareit.booking.dto;

import jakarta.validation.constraints.Future;
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
    @Future
    private LocalDateTime start;
    @Future
    private LocalDateTime end;
    @NotNull
    private Long itemId;
}
