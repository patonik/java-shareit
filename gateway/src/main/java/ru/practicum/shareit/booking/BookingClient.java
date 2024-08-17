package ru.practicum.shareit.booking;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.booking.dto.InwardBookingDto;
import ru.practicum.shareit.client.BaseClient;
import ru.practicum.shareit.exception.MissingValueException;

@Service
public class BookingClient extends BaseClient {
    private static final String API_PREFIX = "/bookings";

    @Autowired
    public BookingClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
            builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                .requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
                .build()
        );
    }

    public ResponseEntity<Object> getBooking(long userId, Long bookingId) {
        return get("/" + bookingId, userId);
    }

    public ResponseEntity<Object> addBooking(InwardBookingDto inwardBookingDto, Long userId) {
        LocalDateTime start = inwardBookingDto.getStart();
        LocalDateTime end = inwardBookingDto.getEnd();
        if (start == null ||
            end == null) {
            throw new MissingValueException();
        }
        return post("", userId, inwardBookingDto);
    }

    public ResponseEntity<Object> approveBooking(Long bookingId, Long userId, Boolean approved) {
        return patch(String.format("/%d?approved=%b", bookingId, approved), userId);
    }

    public ResponseEntity<Object> getUserBookings(Long userId, State state) {
        return get("?state=" + state, userId);
    }

    public ResponseEntity<Object> getOwnerBookings(Long userId, State state) {
        return get("/owner?state=" + state, userId);
    }
}