package ru.practicum.shareit.booking.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.shareit.booking.dto.InwardBookingDto;
import ru.practicum.shareit.booking.dto.OutwardBookingDto;
import ru.practicum.shareit.booking.service.BookingService;

import java.util.List;


/**
 * TODO Sprint add-bookings.
 */
@RestController
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor(onConstructor_ = @__(@Autowired))
@Slf4j
@Validated
public class BookingController {
    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<OutwardBookingDto> addBooking(@RequestBody @NotNull @Valid
                                                        InwardBookingDto inwardBookingDto,
                                                        @RequestHeader("X-Sharer-User-Id") @NotNull Long userId) {
        OutwardBookingDto created = bookingService.addBooking(inwardBookingDto, userId);
        log.info("BookingDto created: {}", created.toString());
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PatchMapping("/{bookingId}")
    public ResponseEntity<OutwardBookingDto> approveBooking(@RequestHeader("X-Sharer-User-Id") @NotNull Long userId,
                                                            @PathVariable Long bookingId,
                                                            @RequestParam Boolean approved) {
        OutwardBookingDto updated = bookingService.approveBooking(bookingId, userId, approved);
        log.info("OutwardBookingDto updated: {}", updated.toString());
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<OutwardBookingDto> getBooking(@RequestHeader("X-Sharer-User-Id") @NotNull Long userId,
                                                        @PathVariable Long bookingId) {
        OutwardBookingDto found = bookingService.getBooking(bookingId, userId);
        log.info("OutwardBookingDto found {}: ", found.toString());
        return new ResponseEntity<>(found, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<OutwardBookingDto>> getUserBookings(
        @RequestHeader("X-Sharer-User-Id") @NotNull Long userId,
        @RequestParam(required = false, defaultValue = "ALL")
        State state) {
        List<OutwardBookingDto> found = bookingService.getUserBookings(userId, state);
        log.info("List<OutwardBookingDto> found: {}", found.toString());
        return new ResponseEntity<>(found, HttpStatus.OK);
    }

    @GetMapping("/owner")
    public ResponseEntity<List<OutwardBookingDto>> getOwnerBookings(
        @RequestHeader("X-Sharer-User-Id") @NotNull Long userId,
        @RequestParam(required = false, defaultValue = "ALL")
        State state) {
        List<OutwardBookingDto> found = bookingService.getOwnerBookings(userId, state);
        log.info("List<OutwardBookingDto> found: {}", found.toString());
        return new ResponseEntity<>(found, HttpStatus.OK);
    }
}
