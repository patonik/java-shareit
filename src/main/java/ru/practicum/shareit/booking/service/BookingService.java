package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.booking.controller.State;
import ru.practicum.shareit.booking.dto.InwardBookingDto;
import ru.practicum.shareit.booking.dto.OutwardBookingDto;

import java.util.List;

public interface BookingService {
    OutwardBookingDto addBooking(InwardBookingDto inwardBookingDto, Long userId);

    OutwardBookingDto approveBooking(Long bookingId, Long userId, Boolean approved);

    OutwardBookingDto getBooking(Long bookingId, Long userId);

    List<OutwardBookingDto> getUserBookings(Long userId, State state);

    List<OutwardBookingDto> getOwnerBookings(Long userId, State state);
}
