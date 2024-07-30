package ru.practicum.shareit.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.shareit.booking.model.Status;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class GetBookingDto {
    private Long itemId;
    private LocalDateTime start;
    private LocalDateTime end;
    private Status status;
}
