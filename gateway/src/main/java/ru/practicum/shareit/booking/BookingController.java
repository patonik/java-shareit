package ru.practicum.shareit.booking;

import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.practicum.shareit.booking.dto.InwardBookingDto;


@Controller
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
@Slf4j
@Validated
public class BookingController {
    private final BookingClient bookingClient;

    @PostMapping
    public ResponseEntity<Object> addBooking(@RequestBody @NotNull @Valid InwardBookingDto inwardBookingDto,
                                                        @RequestHeader("X-Sharer-User-Id") @NotNull Long userId) {
        log.info("requesting server to add booking: {}", inwardBookingDto);
        return bookingClient.addBooking(inwardBookingDto, userId);
    }

    @PatchMapping("/{bookingId}")
    public ResponseEntity<Object> approveBooking(@RequestHeader("X-Sharer-User-Id") @NotNull Long userId,
                                                            @PathVariable Long bookingId,
                                                            @RequestParam Boolean approved) {
        log.info("requesting server to approve booking: {}, {}, {}", userId, bookingId, approved);
        return bookingClient.approveBooking(bookingId, userId, approved);
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<Object> getBooking(@RequestHeader("X-Sharer-User-Id") @NotNull Long userId,
                                                        @PathVariable Long bookingId) {
        log.info("requesting server to get booking: {}, {}", userId, bookingId);
        return bookingClient.getBooking(userId, bookingId);
    }

    @GetMapping
    public ResponseEntity<Object> getUserBookings(
        @RequestHeader("X-Sharer-User-Id") @NotNull Long userId,
        @RequestParam(required = false, defaultValue = "ALL")
        State state) {
        log.info("requesting server to get user bookings: {}, {}", userId, state);
        return bookingClient.getUserBookings(userId, state);
    }

    @GetMapping("/owner")
    public ResponseEntity<Object> getOwnerBookings(
        @RequestHeader("X-Sharer-User-Id") @NotNull Long userId,
        @RequestParam(required = false, defaultValue = "ALL")
        State state) {
        log.info("requesting server to get owner bookings: {}, {}", userId, state);
        return bookingClient.getOwnerBookings(userId, state);
    }
}